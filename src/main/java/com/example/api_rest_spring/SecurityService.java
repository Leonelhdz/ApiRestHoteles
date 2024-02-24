package com.example.api_rest_spring;

import org.springframework.stereotype.Service;
/**
 * Servicio encargado de validar tokens de autenticación.
 */
@Service
    public class SecurityService {
    /**
     * Valida un token de autenticación.
     * @param token El token a validar.
     * @return true si el token es válido, false en caso contrario.
     */
        public Boolean validateToken(String token){
            /* .... */

            return (token.equals("t0k3n"));
        }

    }

