public class ProgramaGestorDeTickets {
    public static void main(String[] args) {
        int eleccionMenu;
        do {
            System.out.println("1: INGRESAR COMO USUARIO\t2: INGRESAR COMO TÉCNICO\t3: INGRESAR COMO ADMINISTRADOR");
            eleccionMenu = Utilidades.inputNumerico();

            if (eleccionMenu < 0 || eleccionMenu > 3) {
                System.out.println("El número introducido no es válido, por favor introduce otro número");
            }

            switch (eleccionMenu) {
                case 1:
                    //ingresarComoUsuario();
                    break;
                case 2:
                    //ingresarComoTecnico();
                    break;
                case 3:
                    //ingresarComoAdministrador();
                    break;
            }

        } while (eleccionMenu != 0);
    }

}
