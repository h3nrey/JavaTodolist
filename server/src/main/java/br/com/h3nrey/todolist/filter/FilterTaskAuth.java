package br.com.h3nrey.todolist.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.h3nrey.todolist.user.IUserRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var servletPath = request.getServletPath();

        // Set<String> protectedRoutes = Set.of("/tasks/create", "/tasks/");
        String protectedRoute = "/tasks/";

        if (servletPath.startsWith(protectedRoute) == false) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get auth info
        var auth = request.getHeader("Authorization");
        var authEncoded = auth.substring("Basic".length()).trim();
        byte[] auth64 = Base64.getDecoder().decode(authEncoded);
        String[] credentials = new String(auth64).split(":");
        String username = credentials[0];
        String password = credentials[1];

        // Validate username
        var user = this.userRepository.findByUsername(username);

        if (user == null) {
            response.sendError(401, "User Not Authorized");
            return;
        }

        // Validate password
        boolean passwordMatchs = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified;

        if (passwordMatchs == false) {
            response.sendError(401, "wrong password");
            return;
        }

        // Next
        request.setAttribute("userId", user.getId());
        filterChain.doFilter(request, response);
    }

}
