using Moq;
using NUnit.Framework;
using ShoeStore.BL.Entities.Customers;
using ShoeStore.BL.UnitTests.Mapper;
using ShoeStore.DataAccess.Entities;
using ShoeStore.DataAccess;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace ShoeStore.BL.UnitTests.Customers
{
    [TestFixture]
    public class CustomersProviderTests
    {
        [Test]
        public void testGetAllCustomers()
        {
            Expression expression = null;
            Mock<IRepository<CustomerEntity>> customersRepository = new Mock<IRepository<CustomerEntity>>();
            customersRepository.Setup(x => x.GetAll(It.IsAny<Expression<Func<CustomerEntity, bool>>>()))
                .Callback((Expression<Func<CustomerEntity, bool>> x) => { expression = x; });
            var customersProvider = new CustomersProvider(customersRepository.Object, MapperHelper.Mapper);
            var customers = customersProvider.GetCustomers();

            customersRepository.Verify(x => x.GetAll(It.IsAny<Expression<Func<CustomerEntity, bool>>>()), Times.Exactly(1));


        }
    }
}
