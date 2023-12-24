using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Mvc.Testing;
using Microsoft.Extensions.DependencyInjection.Extensions;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.VisualStudio.TestPlatform.TestHost;
using ShoeStore.DataAccess;
using ShoeStore.Service.UnitTests.Helpers;
using Microsoft.IdentityModel.Protocols.OpenIdConnect;
using Microsoft.IdentityModel.Protocols;
using Moq;
using ShoeStore.DataAccess.Entities;
using NUnit.Framework;
using System.Drawing;

namespace ShoeStore.Service.UnitTests
{
    public class ShoeStoreServiceTestsBaseClass
    {
        public ShoeStoreServiceTestsBaseClass()
        {
            var settings = TestSettingsHelper.GetSettings();

            _testServer = new TestWebApplicationFactory(services =>
            {
                services.Replace(ServiceDescriptor.Scoped(_ =>
                {
                    var httpClientFactoryMock = new Mock<IHttpClientFactory>();
                    httpClientFactoryMock.Setup(x => x.CreateClient(It.IsAny<string>()))
                        .Returns(TestHttpClient);
                    return httpClientFactoryMock.Object;
                }));
                services.PostConfigureAll<JwtBearerOptions>(options =>
                {
                    options.ConfigurationManager = new ConfigurationManager<OpenIdConnectConfiguration>(
                        $"{settings.IdentityServerUri}/.well-known/openid-configuration",
                        new OpenIdConnectConfigurationRetriever(),
                        new HttpDocumentRetriever(TestHttpClient) //important
                        {
                            RequireHttps = false,
                            SendAdditionalHeaderData = true
                        });
                });
            });
        }

        [OneTimeSetUp]
        public void OneTimeSetup()
        {
            using var scope = GetService<IServiceScopeFactory>().CreateScope();
            var productRepository = scope.ServiceProvider.GetRequiredService<IRepository<ProductEntity>>();
            var brandRepository = scope.ServiceProvider.GetRequiredService<IRepository<ProductBrandEntity>>();
            var brand = brandRepository.Save(new ProductBrandEntity() { Name = "Test brand" });
            var typeRepository = scope.ServiceProvider.GetRequiredService<IRepository<ProductTypeEntity>>();
            var type = typeRepository.Save(new ProductTypeEntity() { Name = "Test type" });

            var product = productRepository.Save(new ProductEntity()
            {
                Name = "testName",
                Description = "testDescription",
                Price = 100,
                PictureUrl = "testPictureUrl",
                ProductBrandId = brand.Id,
                ProductTypeId = type.Id,
                Color = Color.White,
                Size = 40
            });
            TestProductId = product.Id;
        }

        public T? GetService<T>()
        {
            return _testServer.Services.GetRequiredService<T>();
        }

        private readonly WebApplicationFactory<Program> _testServer;
        protected int TestProductId;
        protected HttpClient TestHttpClient => _testServer.CreateClient();
    }
}
