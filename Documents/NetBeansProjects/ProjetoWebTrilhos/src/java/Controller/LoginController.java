package Controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import DAL.Utilizador;
import Model.UtilizadorJpaController;

@Controller
public class LoginController extends AbstractController{

	@RequestMapping(value="/index")
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			String email= request.getParameter("email");
			String password= request.getParameter("password");
		
			DAL.Utilizador modelInstance = Model.UtilizadorJpaController.login(email, password);
			
			//List<DAL.Categoria> categoriasInstance = BLL.Categoria.readAllCategorias();

			ModelAndView mview = null;
			//List<DAL.Filme> filmeInstance = BLL.Filme.readAllFilmes();
			String msg = "";
		
			if(modelInstance == null) {
				msg = "Credenciais erradas!";
				mview = new ModelAndView("home");
				mview.addObject("msg", msg);
				System.out.println("null");
				
			} else if(modelInstance.getUtilizadorTipo().equals(1)) {
				System.out.println("utilizador");
				if(modelInstance.getUtilizadorId()== null) {
					System.out.println("Novo Utilizador");
					//mview = new ModelAndView("listaFilmes");
					mview.addObject("utilizador", modelInstance);
					
					mview.addObject("msg", msg);
				}
			} else if(modelInstance.getUtilizadorTipo().equals(2)){
				System.out.println("Admin");
				mview = new ModelAndView("listaFilmes");
				mview.addObject("utilizador", modelInstance);
				mview.addObject("msg", msg);
			}
		
			return mview;
		
	}
}
