package co.edu.uniquindio.proyecto.utils;

import co.edu.uniquindio.proyecto.dtos.MensajeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FiltroToken extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Configuración de cabeceras para CORS
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, Authorization");
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
//Obtener la URI de la petición que se está realizando
            String requestURI = request.getRequestURI();
//Se obtiene el token de la petición del encabezado del mensaje HTTP
            String token = getToken(request);
            boolean error = true;
            try {
//Si la petición es para la ruta /api/clientes se verifica que el token sea correcto y que el rol sea CLIENTE

                if (requestURI.startsWith("/api/clientes")) {
                    if (token != null) {
                        Jws<Claims> jws = jwtUtils.parseJwt(token);
                        if (!jws.getPayload().get("rol").equals("CLIENTE")) {
                            crearRespuestaError("No tiene permisos para acceder a este recurso",
                                    HttpServletResponse.SC_FORBIDDEN, response);
                        } else {
                            error = false;
                        }
                    } else {
                        crearRespuestaError("No tiene permisos para acceder a este recurso",
                                HttpServletResponse.SC_FORBIDDEN, response);
                    }
                } else {
                    error = false;
                }

                if (requestURI.startsWith("/api/moderadores")) {
                    if (token != null) {
                        Jws<Claims> jws = jwtUtils.parseJwt(token);
                        if (!jws.getPayload().get("rol").equals("MODERADOR")) {
                            crearRespuestaError("No tiene permisos para acceder a este recurso",
                                    HttpServletResponse.SC_FORBIDDEN, response);
                        } else {
                            error = false;
                        }
                    } else {
                        crearRespuestaError("No tiene permisos para acceder a este recurso",
                                HttpServletResponse.SC_FORBIDDEN, response);
                    }
                } else {
                    error = false;
                }
                /*if (requestURI.startsWith("/api/negocios")) {
                    if (token != null) {
                        Jws<Claims> jws = jwtUtils.parseJwt(token);
                        if (!jws.getPayload().get("rol").equals("CLIENTE")) {
                            crearRespuestaError("No tiene permisos para acceder a este recurso",
                                    HttpServletResponse.SC_FORBIDDEN, response);
                        } else {
                            error = false;
                        }
                    } else {
                        crearRespuestaError("No tiene permisos para acceder a este recurso",
                                HttpServletResponse.SC_FORBIDDEN, response);
                    }
                } else {
                    error = false;
                }*/

                if (requestURI.startsWith("/api/comentarios")) {
                    if (token != null) {
                        Jws<Claims> jws = jwtUtils.parseJwt(token);
                        if (!jws.getPayload().get("rol").equals("CLIENTE")) {
                            crearRespuestaError("No tiene permisos para acceder a este recurso",
                                    HttpServletResponse.SC_FORBIDDEN, response);
                        } else {

                            error = false;
                            System.out.println("variable error tiene valor de  =" +  error );
                        }
                    } else {
                        crearRespuestaError("No tiene permisos para acceder a este recurso",
                                HttpServletResponse.SC_FORBIDDEN, response);
                    }
                } else {
                    error = false;
                }

                if (requestURI.startsWith("/api/auth")) {
                    filterChain.doFilter(request, response);
                    return;
                }

                if (requestURI.startsWith("/api/publico")) {
                    filterChain.doFilter(request, response);
                    return;
                }

                if (requestURI.startsWith("/api/negocios")) {
                    filterChain.doFilter(request, response);
                    return;
                }



                if (requestURI.startsWith("/api/comentarios")) {
                    filterChain.doFilter(request, response);
                    return;
                }

                if (requestURI.startsWith("/api/imagenes")) {
                    filterChain.doFilter(request, response);
                    return;
                }


//Agregar más validaciones para otros roles y recursos (rutas de la API) aquí
            } catch (MalformedJwtException | SignatureException e) {
                crearRespuestaError("El token es incorrecto",
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);

            } catch (ExpiredJwtException e) {
                crearRespuestaError("El token está vencido",
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);

            } catch (Exception e) {
                crearRespuestaError(e.getMessage(),
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
            }
            if (!error) {
                filterChain.doFilter(request, response);
            }
        }

    }

    private String getToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer "))
            return header.replace("Bearer ", "");
        return null;
    }

    private void crearRespuestaError(String mensaje, int codigoError, HttpServletResponse
            response) throws IOException {
        MensajeDTO<String> dto = new MensajeDTO<>(true, mensaje);
        response.setContentType("*/*");
        response.setStatus(codigoError);
        response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
