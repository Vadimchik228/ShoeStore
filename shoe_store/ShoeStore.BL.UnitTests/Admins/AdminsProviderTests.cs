using Moq;
using NUnit.Framework;
using ShoeStore.BL.Entities.Admins;
using ShoeStore.BL.UnitTests.Mapper;
using ShoeStore.DataAccess.Entities;
using ShoeStore.DataAccess;
using System.Linq.Expressions;

namespace ShoeStore.BL.UnitTests.Admins
{
    [TestFixture]
    public class AdminsProviderTests
    {
        [Test]
        public void testGetAllAdmins()
        {
            Expression expression = null;
            Mock<IRepository<AdminEntity>> adminsRepository = new Mock<IRepository<AdminEntity>>();
            adminsRepository.Setup(x => x.GetAll(It.IsAny<Expression<Func<AdminEntity, bool>>>()))
                .Callback((Expression<Func<AdminEntity, bool>> x) => { expression = x; });
            var adminsProvider = new AdminsProvider(adminsRepository.Object, MapperHelper.Mapper);
            var admins = adminsProvider.GetAdmins();

            adminsRepository.Verify(x => x.GetAll(It.IsAny<Expression<Func<AdminEntity, bool>>>()), Times.Exactly(1));

        }
    }
}