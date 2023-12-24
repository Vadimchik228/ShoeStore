using FluentAssertions;
using NUnit.Framework;
using ShoeStore.DataAccess;
using ShoeStore.DataAccess.Entities;

namespace ShoeStore.UnitTests.Repository
{
    [TestFixture]
    [Category("Integration")]
    public class CustomerRepositoryTests : RepositoryTestBase
    {
        [Test]
        public void GetAllCustomersTest()
        {
            //prepare
            using var context = DbContextFactory.CreateDbContext();

            var customers = new CustomerEntity[]
            {
            new CustomerEntity()
            {
                Surname = "Test1",
                Name = "Test1",
                Patronymic = "Test1",
                PhoneNumber = "Test1",
                Email = "Test1",
                PasswordHash = "Test1",
                ExternalId = Guid.NewGuid()
            },
            new CustomerEntity()
            {
                Surname = "Test2",
                Name = "Test2",
                Patronymic = "Test2",
                PhoneNumber = "Test2",
                Email = "Test2",
                PasswordHash = "Test2",
                ExternalId = Guid.NewGuid()
            }
            };
            context.Customers.AddRange(customers);
            context.SaveChanges();

            //execute
            var repository = new Repository<CustomerEntity>(DbContextFactory);
            var actualCustomers = repository.GetAll();

            //assert        
            actualCustomers.Should().BeEquivalentTo(customers);
        }

        [Test]
        public void GetAllCustomersWithFilterTest()
        {
            //prepare
            using var context = DbContextFactory.CreateDbContext();

            var customers = new CustomerEntity[]
            {
            new CustomerEntity()
            {
                Surname = "Test1",
                Name = "Test1",
                Patronymic = "Test1",
                PhoneNumber = "Test1",
                Email = "Test1",
                PasswordHash = "Test1",
                ExternalId = Guid.NewGuid()
            },
            new CustomerEntity()
            {
                Surname = "Test2",
                Name = "Test2",
                Patronymic = "Test2",
                PhoneNumber = "Test2",
                Email = "Test2",
                PasswordHash = "Test2",
                ExternalId = Guid.NewGuid()
            }
            };
            context.Customers.AddRange(customers);
            context.SaveChanges();
            //execute

            var repository = new Repository<CustomerEntity>(DbContextFactory);
            var actualCustomers = repository.GetAll(x => x.Surname == "Test2").ToArray();

            //assert
            actualCustomers.Should().BeEquivalentTo(customers.Where(x => x.Surname == "Test2"));
        }

        [Test]
        public void SaveNewCustomersTest()
        {
            //prepare
            using var context = DbContextFactory.CreateDbContext();

            //execute

            var customer = new CustomerEntity()
            {
                Surname = "Test1",
                Name = "Test1",
                Patronymic = "Test1",
                PhoneNumber = "Test1",
                Email = "Test1",
                PasswordHash = "Test1",
                ExternalId = Guid.NewGuid()
            };
            var repository = new Repository<CustomerEntity>(DbContextFactory);
            repository.Save(customer);

            //assert
            var actualCustomer = context.Customers.SingleOrDefault();
            actualCustomer.Should().BeEquivalentTo(customer, options => options
                .Excluding(x => x.Id)
                .Excluding(x => x.ModificationTime)
                .Excluding(x => x.CreationTime)
                .Excluding(x => x.ExternalId));
            actualCustomer.Id.Should().NotBe(default);
            actualCustomer.ModificationTime.Should().NotBe(default);
            actualCustomer.CreationTime.Should().NotBe(default);
            actualCustomer.ExternalId.Should().NotBe(Guid.Empty);
        }

        [Test]
        public void UpdateCustomerTest()
        {
            //prepare
            using var context = DbContextFactory.CreateDbContext();

            var customer = new CustomerEntity()
            {
                Surname = "Test1",
                Name = "Test1",
                Patronymic = "Test1",
                PhoneNumber = "Test1",
                Email = "Test1",
                PasswordHash = "Test1",
                ExternalId = Guid.NewGuid()
            };
            context.Customers.Add(customer);
            context.SaveChanges();

            //execute

            customer.Surname = "new surname";
            customer.Name = "new name";
            customer.Patronymic = "new patronimyc";
            var repository = new Repository<CustomerEntity>(DbContextFactory);
            repository.Save(customer);

            //assert
            var actualCustomer = context.Customers.SingleOrDefault();
            actualCustomer.Should().BeEquivalentTo(customer);
        }


        [Test]
        public void DeleteCustomerTest()
        {
            //prepare
            using var context = DbContextFactory.CreateDbContext();

            var customer = new CustomerEntity()
            {
                Surname = "Test1",
                Name = "Test1",
                Patronymic = "Test1",
                PhoneNumber = "Test1",
                Email = "Test1",
                PasswordHash = "Test1",
                ExternalId = Guid.NewGuid()
            };
            context.Customers.Add(customer);
            context.SaveChanges();

            //execute

            var repository = new Repository<CustomerEntity>(DbContextFactory);
            repository.Delete(customer);

            //assert
            context.Customers.Count().Should().Be(0);
        }

        [Test]
        public void GetByIdTest_PositiveCase()
        {
            //prepare
            using var context = DbContextFactory.CreateDbContext();
            var customers = new CustomerEntity[]
            {
            new CustomerEntity()
            {
                Surname = "Test1",
                Name = "Test1",
                Patronymic = "Test1",
                PhoneNumber = "Test1",
                Email = "Test1",
                PasswordHash = "Test1",
                ExternalId = Guid.NewGuid()
            },
            new CustomerEntity()
            {
                Surname = "Test2",
                Name = "Test2",
                Patronymic = "Test2",
                PhoneNumber = "Test2",
                Email = "Test2",
                PasswordHash = "Test2",
                ExternalId = Guid.NewGuid()
            }
            };
            context.Customers.AddRange(customers);
            context.SaveChanges();

            //execute
            var repository = new Repository<CustomerEntity>(DbContextFactory);
            var actualCustomer = repository.GetById(customers[0].Id);

            //assert
            actualCustomer.Should().BeEquivalentTo(customers[0]);
        }
        [Test]
        public void GetByIdTest_NegativeCase()
        {
            //prepare
            using var context = DbContextFactory.CreateDbContext();
            var customers = new CustomerEntity[]
            {
            new CustomerEntity()
            {
                Surname = "Test1",
                Name = "Test1",
                Patronymic = "Test1",
                PhoneNumber = "Test1",
                Email = "Test1",
                PasswordHash = "Test1",
                ExternalId = Guid.NewGuid()
            },
            new CustomerEntity()
            {
                Surname = "Test2",
                Name = "Test2",
                Patronymic = "Test2",
                PhoneNumber = "Test2",
                Email = "Test2",
                PasswordHash = "Test2",
                ExternalId = Guid.NewGuid()
            }
            };
            context.Customers.AddRange(customers);
            context.SaveChanges();

            //execute
            var repository = new Repository<CustomerEntity>(DbContextFactory);
            var actualCustomer = repository.GetById(customers[customers.Length - 1].Id + 1);

            //assert
            actualCustomer.Should().BeNull();
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
                context.Customers.RemoveRange(context.Customers);
                context.SaveChanges();
            }
        }
    }
}

