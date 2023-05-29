package miu.edu.service;


import miu.edu.dto.AuthDTO.AuthLoginDTO;
import miu.edu.dto.AuthDTO.AuthRequestDTO;
import miu.edu.dto.AuthDTO.AuthResponseDTO;


public interface AuthenticationService  {
    AuthResponseDTO authenticate(AuthLoginDTO authLoginDTO);
}
