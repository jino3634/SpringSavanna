package jp.co.sss.shop.controller.login;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
//
//import javax.servlet.http.HttpSession;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
//import jp.co.sss.shop.bean.UserBean;
//import jp.co.sss.shop.form.LoginForm;
//import jp.co.sss.shop.repository.UserRepository;
//
//public class LoginControllerTest {
//
//    private MockMvc mockMvc;
//    
//    @Mock
//    private UserRepository userRepository;
//    
//    @Mock
//    private HttpSession session;
//    
//    @BeforeEach
//    public void setUp() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/views/");
//        viewResolver.setSuffix(".jsp");
//
//        mockMvc = MockMvcBuilders.standaloneSetup(new LoginController())
//                                 .setViewResolvers(viewResolver)
//                                 .build();
//    }
//
//    @Test
//    public void testLogin() throws Exception {
//        mockMvc.perform(get("/login"))
//               .andExpect(status().isOk())
//               .andExpect(view().name("login"))
//               .andExpect(model().attributeExists("loginForm"));
//    }
//
//    @Test
//    public void testDoLoginAsNormalUser() throws Exception {
//        // set up
//        UserBean user = new UserBean();
//        user.setAuthority(2);
//        LoginForm form = new LoginForm();
//        MockHttpServletRequestBuilder requestBuilder = post("/login")
//                .flashAttr("loginForm", form)
//                .sessionAttr("user", user);
//        
//        // execute
//        MvcResult result = mockMvc.perform(requestBuilder)
//                                  .andReturn();
//        
//        // verify
//        BindingResult bindingResult = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.loginForm");
//        if (bindingResult != null) {
//            assert(bindingResult.hasErrors() == false);
//        }
//        mockMvc.perform(requestBuilder)
//               .andExpect(status().is3xxRedirection())
//               .andExpect(redirectedUrl("/"));
//    }
//    
//    @Test
//    public void testDoLoginAsAdminUser() throws Exception {
//        // set up
//        UserBean user = new UserBean();
//        user.setAuthority(1);
//        LoginForm form = new LoginForm();
//        MockHttpServletRequestBuilder requestBuilder = post("/login")
//                .flashAttr("loginForm", form)
//                .sessionAttr("user", user);
//        
//        // execute
//        MvcResult result = mockMvc.perform(requestBuilder)
//                                  .andReturn();
//        
//        // verify
//        BindingResult bindingResult = (BindingResult) result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.loginForm");
//        if (bindingResult != null) {
//            assert(bindingResult.hasErrors() == false);
//        }
//        mockMvc.perform(requestBuilder)
//               .andExpect(status().isOk())
//               .andExpect(view().name("admin_menu"));
//    }
//}