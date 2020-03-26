import utils.Container;
import visualizacoes.TelaInicio;

public class AppCristalGym {

    public static void main(String[] args) {
        Container.buscarDependencia(TelaInicio.class).iniciarSistema();
    }
}
