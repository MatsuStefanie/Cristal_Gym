package utils;

import controladores.BiotipoControlador;
import controladores.ClienteControlador;
import controladores.EmailControlador;
import dao.BiotipoDAO;
import dao.ClienteDAO;
import dao.EmailDAO;
import dao.config.Conexao;
import visualizacoes.BiotipoVisualizador;
import visualizacoes.ClienteVisualizador;
import visualizacoes.EmailVisualizador;
import visualizacoes.TelaInicio;

import java.util.ArrayList;
import java.util.List;

public final class Container {

    private static  List dependencias = new ArrayList<>();

    private Container() {}

    static {
        init();
    }

    private static void init() {

        Conexao conexao = new Conexao();
        dependencias.add(conexao);

        EmailDAO emailDAO = new EmailDAO(conexao);
        EmailControlador emailControlador = new EmailControlador(emailDAO);
        EmailVisualizador emailVisualizador = new EmailVisualizador();
        dependencias.add(emailDAO);
        dependencias.add(emailControlador);
        dependencias.add(emailVisualizador);

        ClienteDAO clienteDAO = new ClienteDAO(conexao);
        ClienteControlador clienteControlador = new ClienteControlador(clienteDAO);
        ClienteVisualizador clienteVisualizador = new ClienteVisualizador();
        dependencias.add(clienteDAO);
        dependencias.add(clienteControlador);
        dependencias.add(clienteVisualizador);

        BiotipoDAO biotipoDAO = new BiotipoDAO(conexao);
        BiotipoControlador biotipoControlador = new BiotipoControlador(biotipoDAO);
        BiotipoVisualizador biotipoVisualizador = new BiotipoVisualizador();
        dependencias.add(biotipoDAO);
        dependencias.add(biotipoControlador);
        dependencias.add(biotipoVisualizador);

        TelaInicio telaInicio = new TelaInicio();
        dependencias.add(telaInicio);
    }

    public static <T> T buscarDependencia(Class<T> classDependencia) {
        Object dependencia = dependencias
                .stream()
                .filter(depend ->
                        (depend.getClass().getName().equals(classDependencia.getName()))
                )
                .findFirst()
                .orElse(null);

        if( dependencia != null) {
            return (T) dependencia ;
        }

        throw new RuntimeException("Dependencia não mapeada : " + classDependencia.getName());
    }
}