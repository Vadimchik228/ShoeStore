using Microsoft.EntityFrameworkCore;
using ShoeStore.DataAccess.Entities;

namespace ShoeStore.DataAccess;

public class ShoeStoreDbContext : DbContext
{
    public DbSet<AdminEntity> Admins { get; set; }
    public DbSet<CustomerEntity> Customers { get; set; }
    public DbSet<OrderEntity> Orders { get; set; }
    public DbSet<PointOfDeliveryEntity> PointsOfDelivery { get; set; }
    public DbSet<PromocodeEntity> Promocodes { get; set; }
    public DbSet<ProductEntity> Products { get; set; }
    public DbSet<ProductBrandEntity> Brands { get; set; }
    public DbSet<ProductTypeEntity> Types { get; set; }
    public DbSet<FeedbackEntity> Feedbacks { get; set; }
    public DbSet<ShoppingBasketEntity> ShoppingBaskets { get; set; }

    public ShoeStoreDbContext(DbContextOptions options) : base(options)
    {
    }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        // Admins
        modelBuilder.Entity<AdminEntity>().HasKey(x => x.Id);
        modelBuilder.Entity<AdminEntity>().HasIndex(x => x.ExternalId).IsUnique();

        // Customers
        modelBuilder.Entity<CustomerEntity>().HasKey(x => x.Id);
        modelBuilder.Entity<CustomerEntity>().HasIndex(x => x.ExternalId).IsUnique();

        // Brands
        modelBuilder.Entity<ProductBrandEntity>().HasKey(x => x.Id);
        modelBuilder.Entity<ProductBrandEntity>().HasIndex(x => x.ExternalId).IsUnique();

        // Types
        modelBuilder.Entity<ProductTypeEntity>().HasKey(x => x.Id);
        modelBuilder.Entity<ProductTypeEntity>().HasIndex(x => x.ExternalId).IsUnique();

        // Orders
        modelBuilder.Entity<OrderEntity>().HasKey(x => x.Id);
        modelBuilder.Entity<OrderEntity>().HasIndex(x => x.ExternalId).IsUnique();
        modelBuilder.Entity<OrderEntity>().HasOne(x => x.ShoppingBasket)
            .WithMany(x => x.Orders)
            .HasForeignKey(x => x.ShoppingBasketId);
        modelBuilder.Entity<OrderEntity>().HasOne(x => x.PointOfDelivery)
            .WithMany(x => x.Orders)
            .HasForeignKey(x => x.PointOfDeliveryId);
        modelBuilder.Entity<OrderEntity>().HasOne(x => x.Promocode)
            .WithMany(x => x.Orders)
            .HasForeignKey(x => x.PromocodeId);

        // PointsOfDelivery
        modelBuilder.Entity<PointOfDeliveryEntity>().HasKey(x => x.Id);
        modelBuilder.Entity<PointOfDeliveryEntity>().HasIndex(x => x.ExternalId).IsUnique();

        // Products
        modelBuilder.Entity<ProductEntity>().HasKey(x => x.Id);
        modelBuilder.Entity<ProductEntity>().HasIndex(x => x.ExternalId).IsUnique();
        modelBuilder.Entity<ProductEntity>().HasOne(x => x.ProductBrand)
            .WithMany(x => x.Products)
            .HasForeignKey(x => x.ProductBrandId);
        modelBuilder.Entity<ProductEntity>().HasOne(x => x.ProductType)
            .WithMany(x => x.Products)
            .HasForeignKey(x => x.ProductTypeId);

        // Feedbacks
        modelBuilder.Entity<FeedbackEntity>().HasKey(x => x.Id);
        modelBuilder.Entity<FeedbackEntity>().HasIndex(x => x.ExternalId).IsUnique();
        modelBuilder.Entity<FeedbackEntity>().HasOne(x => x.Customer)
            .WithMany(x => x.Feedbacks)
            .HasForeignKey(x => x.CustomerId);
        modelBuilder.Entity<FeedbackEntity>().HasOne(x => x.Product)
                .WithMany(x => x.Feedbacks)
                .HasForeignKey(x => x.ProductId);

        // ShoppingBaskets
        modelBuilder.Entity<ShoppingBasketEntity>().HasKey(x => x.Id);
        modelBuilder.Entity<ShoppingBasketEntity>().HasIndex(x => x.ExternalId).IsUnique();
        modelBuilder.Entity<ShoppingBasketEntity>().HasOne(x => x.Customer)
            .WithMany(x => x.ShoppingBaskets)
            .HasForeignKey(x => x.CustomerId);
        modelBuilder.Entity<ShoppingBasketEntity>().HasOne(x => x.Product)
            .WithMany(x => x.ShoppingBaskets)
            .HasForeignKey(x => x.ProductId);

        // Promocodes
        modelBuilder.Entity<PromocodeEntity>().HasKey(x => x.Id);
        modelBuilder.Entity<PromocodeEntity>().HasIndex(x => x.ExternalId).IsUnique();
    }
}

