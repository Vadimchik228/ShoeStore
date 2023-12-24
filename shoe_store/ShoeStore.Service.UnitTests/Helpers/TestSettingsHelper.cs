using ShoeStore.Service.Settings;

namespace ShoeStore.Service.UnitTests.Helpers
{
    public static class TestSettingsHelper
    {
        public static ShoeStoreSettings GetSettings()
        {
            return ShoeStoreSettingsReader.Read(ConfigurationHelper.GetConfiguration());
        }
    }
}
