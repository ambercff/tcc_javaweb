package Controller;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import Model.RegCycleDAO;
import Model.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RegCycleDAO regcycleDAO;

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("regcycle-jpa");

	@Override
	public void init() throws ServletException {
		regcycleDAO = new RegCycleDAO(emf);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operacao = request.getParameter("operacao");
		operacao = operacao.toLowerCase();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = request.getParameter("usernameCampo");
		Usuario usuario = regcycleDAO.encontrarUsuario(username);

		if (usuario == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			request.setAttribute("status", false);
			dispatcher.forward(request, response);
			
			return;
		}
	
		String senha = request.getParameter("senhaCampo");
		
		if (!usuario.getSenha().equals(senha)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			request.setAttribute("status", false);
			dispatcher.forward(request, response);
			return;
		}
		
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(99999);
		session.setAttribute("usuario", usuario);
		response.sendRedirect("PainelController");
	
	}
	
	
}