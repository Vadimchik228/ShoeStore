using NUnit.Framework;
using ShoeStore.DataAccess.Entities;
using ShoeStore.DataAccess;
using System.Drawing;
using FluentAssertions;

namespace ShoeStore.UnitTests.Repository
{
    [TestFixture]
    [Category("Integration")]
    public class ProductRepositoryTests : RepositoryTestBase
    {
        [Test]
        public void GetAllProductsTest()
        {
            //prepare
            using var context = DbContextFactory.CreateDbContext();
            var brand = new ProductBrandEntity()
            {
                Name = "My Brand",
                ExternalId = Guid.NewGuid()
            };
            context.Brands.Add(brand);
            context.SaveChanges();

            var type = new ProductTypeEntity()
            {
                Name = "My type",
                ExternalId = Guid.NewGuid()
            };
            context.Types.Add(type);
            context.SaveChanges();

            var products = new ProductEntity[]
                {
            new ProductEntity()
            {
                Name = "Test1",
                Price = 500,
                PictureUrl = "Test1",
                Description = "Test1",
                ProductBrandId = brand.Id,
                ProductTypeId = type.Id,
                Color = Color.White,
                Size = 40,
                ExternalId = Guid.NewGuid()
            },
            new ProductEntity()
            {
                Name = "Test2",
                Price = 550,
                PictureUrl = "Test2",
                Description = "Test2",
                ProductBrandId = brand.Id,
                ProductTypeId = type.Id,
                Color = Color.White,
                Size = 42,
                ExternalId = Guid.NewGuid()
            }
            };
            context.Products.AddRange(products);
            context.SaveChanges();

            //execute
            var repository = new Repository<ProductEntity>(DbContextFactory);
            var actualProducts = repository.GetAll();

            //assert        
            actualProducts.Should().BeEquivalentTo(products, options => options.Excluding(x => x.Description)
                .Excluding(x => x.ProductBrand)
                .Excluding(x => x.ProductType));
        }

        [Test]
        public void GetAllProductsWithFilterTest()
        {
            //prepare
            using var context = DbContextFactory.CreateDbContext();
            var brand = new ProductBrandEntity()
            {
                Name = "My Brand",
                ExternalId = Guid.NewGuid()
            };
            context.Brands.Add(brand);
            context.SaveChanges();

            var type = new ProductTypeEntity()
            {
                Name = "My type",
                ExternalId = Guid.NewGuid()
            };
            context.Types.Add(type);
            context.SaveChanges();

            var products = new ProductEntity[]
                {
            new ProductEntity()
            {
                Name = "Test1",
                Price = 500,
                PictureUrl = "Test1",
                Description = "Test1",
                ProductBrandId = brand.Id,
                ProductTypeId = type.Id,
                Color = Color.White,
                Size = 40,
                ExternalId = Guid.NewGuid()
            },
            new ProductEntity()
            {
                Name = "Test2",
                Price = 550,
                PictureUrl = "Test2",
                Description = "Test2",
                ProductBrandId = brand.Id,
                ProductTypeId = type.Id,
                Color = Color.White,
                Size = 42,
                ExternalId = Guid.NewGuid()
            }
            };
            context.Products.AddRange(products);
            context.SaveChanges();
            //execute

            var repository = new Repository<ProductEntity>(DbContextFactory);
            var actualProducts = repository.GetAll(x => x.Name == "Test2").ToArray();

            //assert
            actualProducts.Should().BeEquivalentTo(products.Where(x => x.Name == "Test2"),
                options => options.Excluding(x => x.Description)
                .Excluding(x => x.ProductBrand)
                .Excluding(x => x.ProductType));
        }

        [Test]
        public void SaveNewProductTest()
        {
            //prepare
            using var context = DbContextFactory.CreateDbContext();
            var brand = new ProductBrandEntity()
            {
                Name = "My Brand",
                ExternalId = Guid.NewGuid()
            };
            context.Brands.Add(brand);
            context.SaveChanges();

            var type = new ProductTypeEntity()
            {
                Name = "My type",
                ExternalId = Guid.NewGuid()
            };
            context.Types.Add(type);
            context.SaveChanges();

            //execute

            var product = new ProductEntity()
            {
                Name = "Test1",
                Price = 500,
                PictureUrl = "Test1",
                Description = "Test1",
                ProductBrandId = brand.Id,
                ProductTypeId = type.Id,
                Color = Color.White,
                Size = 40,
                ExternalId = Guid.NewGuid()
            };
            var repository = new Repository<ProductEntity>(DbContextFactory);
            repository.Save(product);

            //assert
            var actualProduct = context.Products.SingleOrDefault();
            actualProduct.Should().BeEquivalentTo(product, options => options.Excluding(x => x.Description)
                .Excluding(x => x.ProductBrand)
                .Excluding(x => x.ProductType)
                .Excluding(x => x.Id)
                .Excluding(x => x.ModificationTime)
                .Excluding(x => x.CreationTime)
                .Excluding(x => x.ExternalId));
            actualProduct.Id.Should().NotBe(default);
            actualProduct.ModificationTime.Should().NotBe(default);
            actualProduct.CreationTime.Should().NotBe(default);
            actualProduct.ExternalId.Should().NotBe(Guid.Empty);
        }

        [Test]
        public void UpdateProductTest()
        {
            //prepare
            using var context = DbContextFactory.CreateDbContext();
            var brand = new ProductBrandEntity()
            {
                Name = "My Brand",
                ExternalId = Guid.NewGuid()
            };
            context.Brands.Add(brand);
            context.SaveChanges();

            var type = new ProductTypeEntity()
            {
                Name = "My type",
                ExternalId = Guid.NewGuid()
            };
            context.Types.Add(type);
            context.SaveChanges();

            //execute

            var product = new ProductEntity()
            {
                Name = "Test1",
                Price = 500,
                PictureUrl = "Test1",
                Description = "Test1",
                ProductBrandId = brand.Id,
                ProductTypeId = type.Id,
                Color = Color.White,
                Size = 40,
                ExternalId = Guid.NewGuid()
            };
            context.Products.Add(product);
            context.SaveChanges();

            //execute

            product.Name = "new Name";
            product.Price = 700;
            product.Description = "new Description";
            var repository = new Repository<ProductEntity>(DbContextFactory);
            repository.Save(product);

            //assert
            var actualProduct = context.Products.SingleOrDefault();
            actualProduct.Should().BeEquivalentTo(product, options => options.Excluding(x => x.Description)
                .Excluding(x => x.ProductBrand)
                .Excluding(x => x.ProductType));
        }


        [Test]
        public void DeleteProductTest()
        {
            //prepare
            using var context = DbContextFactory.CreateDbContext();
            var brand = new ProductBrandEntity()
            {
                Name = "My Brand",
                ExternalId = Guid.NewGuid()
            };
            context.Brands.Add(brand);
            context.SaveChanges();

            var type = new ProductTypeEntity()
            {
                Name = "My type",
                ExternalId = Guid.NewGuid()
            };
            context.Types.Add(type);
            context.SaveChanges();

            //execute

            var product = new ProductEntity()
            {
                Name = "Test1",
                Price = 500,
                PictureUrl = "Test1",
                Description = "Test1",
                ProductBrandId = brand.Id,
                ProductTypeId = type.Id,
                Color = Color.White,
                Size = 40,
                ExternalId = Guid.NewGuid()
            };
            context.Products.Add(product);
            context.SaveChanges();

            //execute

            var repository = new Repository<ProductEntity>(DbContextFactory);
            repository.Delete(product);

            //assert
            context.Products.Count().Should().Be(0);
        }

        [Test]
        public void GetByIdTest_PositiveCase()
        {
            //prepare
            using var context = DbContextFactory.CreateDbContext();
            var brand = new ProductBrandEntity()
            {
                Name = "My Brand",
                ExternalId = Guid.NewGuid()
            };
            context.Brands.Add(brand);
            context.SaveChanges();

            var type = new ProductTypeEntity()
            {
                Name = "My type",
                ExternalId = Guid.NewGuid()
            };
            context.Types.Add(type);
            context.SaveChanges();

            var products = new ProductEntity[]
                {
            new ProductEntity()
            {
                Name = "Test1",
                Price = 500,
                PictureUrl = "Test1",
                Description = "Test1",
                ProductBrandId = brand.Id,
                ProductTypeId = type.Id,
                Color = Color.White,
                Size = 40,
                ExternalId = Guid.NewGuid()
            },
            new ProductEntity()
            {
                Name = "Test2",
                Price = 550,
                PictureUrl = "Test2",
                Description = "Test2",
                ProductBrandId = brand.Id,
                ProductTypeId = type.Id,
                Color = Color.White,
                Size = 42,
                ExternalId = Guid.NewGuid()
            }
            };
            context.Products.AddRange(products);
            context.SaveChanges();

            //execute
            var repository = new Repository<ProductEntity>(DbContextFactory);
            var actualProduct = repository.GetById(products[0].Id);

            //assert
            actualProduct.Should().BeEquivalentTo(products[0], options => options.Excluding(x => x.Description)
                .Excluding(x => x.ProductBrand)
                .Excluding(x => x.ProductType));
        }
        [Test]
        public void GetByIdTest_NegativeCase()
        {
            //prepare
            using var context = DbContextFactory.CreateDbContext();
            var brand = new ProductBrandEntity()
            {
                Name = "My Brand",
                ExternalId = Guid.NewGuid()
            };
            context.Brands.Add(brand);
            context.SaveChanges();

            var type = new ProductTypeEntity()
            {
                Name = "My type",
                ExternalId = Guid.NewGuid()
            };
            context.Types.Add(type);
            context.SaveChanges();

            var products = new ProductEntity[]
                {
            new ProductEntity()
            {
                Name = "Test1",
                Price = 500,
                PictureUrl = "Test1",
                Description = "Test1",
                ProductBrandId = brand.Id,
                ProductTypeId = type.Id,
                Color = Color.White,
                Size = 40,
                ExternalId = Guid.NewGuid()
            },
            new ProductEntity()
            {
                Name = "Test2",
                Price = 550,
                PictureUrl = "Test2",
                Description = "Test2",
                ProductBrandId = brand.Id,
                ProductTypeId = type.Id,
                Color = Color.White,
                Size = 42,
                ExternalId = Guid.NewGuid()
            }
            };
            context.Products.AddRange(products);
            context.SaveChanges();

            //execute
            var repository = new Repository<ProductEntity>(DbContextFactory);
            var actualProduct = repository.GetById(products[products.Length - 1].Id + 1);

            //assert
            actualProduct.Should().BeNull();
        }

        [SetUp]
        public void SetUp()
        {
            CleanUp();
        }

        [TearDown]
        public void TearDown()
        {
            CleanUp();
        }

        public void CleanUp()
        {
            using (var context = DbContextFactory.CreateDbContext())
            {
                context.Products.RemoveRange(context.Products);
                context.SaveChanges();
            }
        }
    }
}
