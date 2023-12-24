using Microsoft.AspNetCore.Mvc;
using ShoeStore.BL.Auth;

namespace ShoeStore.Service.Controllers
{
    public class AuthController : ControllerBase
    {
        private readonly IAuthProvider _authProvider;

        public AuthController(IAuthProvider authProvider)
        {
            _authProvider = authProvider;
        }

        [HttpGet]
        [Route("login")]
        public async Task<IActionResult> LoginCustomer(string email, string password)
        {
            var tokens = await _authProvider.AuthorizeCustomer(email, password);
            return Ok(tokens);
        }

        [HttpPost]
        [Route("register")]
        public async Task<IActionResult> RegisterCustomer(string email, string password)
        {
            await _authProvider.RegisterCustomer(email, password);
            return Ok();
        }
    }
}
