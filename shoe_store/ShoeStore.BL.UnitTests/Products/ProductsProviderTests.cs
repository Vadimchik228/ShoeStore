using Moq;
using NUnit.Framework;
using ShoeStore.BL.Entities.Products;
using ShoeStore.BL.UnitTests.Mapper;
using ShoeStore.DataAccess.Entities;
using ShoeStore.DataAccess;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace ShoeStore.BL.UnitTests.Products
{
    [TestFixture]
    public class ProductsProviderTests
    {
        [Test]
        public void testGetAllProducts()
        {
            Expression expression = null;
            Mock<IRepository<ProductEntity>> productsRepository = new Mock<IRepository<ProductEntity>>();
            productsRepository.Setup(x => x.GetAll(It.IsAny<Expression<Func<ProductEntity, bool>>>()))
                .Callback((Expression<Func<ProductEntity, bool>> x) => { expression = x; });
            var productsProvider = new ProductsProvider(productsRepository.Object, MapperHelper.Mapper);
            var products = productsProvider.GetProducts();

            productsRepository.Verify(x => x.GetAll(It.IsAny<Expression<Func<ProductEntity, bool>>>()), Times.Exactly(1));


        }
    }
}
