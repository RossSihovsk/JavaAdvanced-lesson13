package filters;

import doMain.AccessLevel;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Arrays;

@WebFilter("/createMagazine.jsp")
public class CreatingMagazinesFilter  implements Filter {

    private FilterService filterService = FilterService.getFilterService();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        filterService.doFilterValidation(request, response, chain, Arrays.asList(AccessLevel.ADMIN));
    }

}
