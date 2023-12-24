using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using ShoeStore.BL.Entities.Customers.Entities;
using ShoeStore.BL.Entities.Customers;
using ShoeStore.Service.Controllers.Entities;

namespace ShoeStore.Service.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class CustomerController : Controller
    {
        private readonly ICustomersProvider _customersProvider;
        private readonly ICustomersManager _customersManager;
        private readonly IMapper _mapper;
        private readonly ILogger _logger;

        public CustomerController(ICustomersProvider productsProvider, ICustomersManager productsManager, IMapper mapper, ILogger logger)
        {
            _customersManager = productsManager;
            _customersProvider = productsProvider;
            _mapper = mapper;
            _logger = logger;
        }

        [HttpGet] //customers/
        public IActionResult GetAllCustomers()
        {
            var customers = _customersProvider.GetCustomers();
            return Ok(new CustomersListResponce()
            {
                Customers = customers.ToList()
            });
        }

        [HttpGet]
        [Route("filter")] 
        public IActionResult GetFilteredCustomers([FromQuery] CustomersFilter filter)
        {
            var customers = _customersProvider.GetCustomers(_mapper.Map<CustomerModelFilter>(filter));
            return Ok(new CustomersListResponce()
            {
                Customers = customers.ToList()
            });
        }

        [HttpGet]
        [Route("{id}")] //customers/{id}
        public IActionResult GetCustomerInfo([FromRoute] Guid id)
        {
            try
            {
                var customer = _customersProvider.GetCustomerInfo(id);
                return Ok(customer);
            }
            catch (ArgumentException ex)
            {
                _logger.LogError(ex.ToString());
                return NotFound(ex.Message);
            }
        }

        [HttpPost]
        public IActionResult CreateCustomer([FromBody] CreateCustomerRequest request)
        {
            try
            {
                var customer = _customersManager.CreateCustomer(_mapper.Map<CreateCustomerModel>(request));
                return Ok(customer);
            }
            catch (ArgumentException ex)
            {
                _logger.LogError(ex.ToString());
                return BadRequest(ex.Message);
            }
        }

        [HttpPut]
        [Route("{id}")]
        public IActionResult UpdateCustomerInfo([FromRoute] Guid id, UpdateCustomerRequest request)
        {
            try
            {
                var customer = _customersManager.UpdateCustomer(id, _mapper.Map<UpdateCustomerModel>(request));
                return Ok(customer);
            }
            catch (ArgumentException ex)
            {
                _logger.LogError(ex.ToString());
                return BadRequest(ex.Message);
            }
        }

        [HttpDelete]
        [Route("{id}")]
        public IActionResult DeleteCustomer([FromRoute] Guid id)
        {
            try
            {
                _customersManager.DeleteCustomer(id);
                return Ok();
            }
            catch (ArgumentException ex)
            {
                _logger.LogError(ex.ToString());
                return BadRequest(ex.Message);

            }
        }
    }
}
