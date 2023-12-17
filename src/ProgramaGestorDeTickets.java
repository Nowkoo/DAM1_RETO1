public class ProgramaGestorDeTickets {
    public static void main(String[] args) {
        int eleccionMenu;
        do {
            System.out.println("0: CERRAR PROGRAMA\n1: INGRESAR COMO USUARIO\n2: INGRESAR COMO TÉCNICO\n3: INGRESAR COMO ADMINISTRADOR");
            eleccionMenu = Utilidades.inputNumerico();

            if (eleccionMenu < 0 || eleccionMenu > 3) {
                System.out.println("El número introducido no es válido, por favor introduce otro número");
            }

            switch (eleccionMenu) {
                case 1:
                    ModuloUsuario.ingresarComoUsuario();
                    break;
                case 2:
                    ModuloTecnico.ingresarComoTecnico();
                    break;
                case 3:
                    ModuloAdmin.ingresarComoAdmin();
                    break;
            }

        } while (eleccionMenu != 0);
    }

}
