package com.udb.autores.directorioautores.view;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validador JSF personalizado para números de teléfono de El Salvador.
 * Formato: Inicia con [2, 3, 6, 7], seguido de 3 dígitos, guion, y 4 dígitos.
 * Ej: 7123-4567
 */
@FacesValidator("svPhoneValidator") // ID único para usarlo en la Vista (XHTML)
public class SVPhoneValidator implements Validator {

    // Compilamos la expresión regular (Regex) una sola vez para eficiencia.
    // ^[2367] -> Debe empezar (^) con 2, 3, 6, o 7
    // \\d{3}  -> Seguido de exactamente 3 dígitos numéricos
    // -       -> Seguido de un guion
    // \\d{4}  -> Seguido de exactamente 4 dígitos numéricos
    // $       -> Debe terminar ($) ahí
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[2367]\\d{3}-\\d{4}$");

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (value == null) {
            // No validar si el valor es nulo (required="true" se encarga de eso)
            return;
        }

        String phone = value.toString();

        // Si el campo está vacío, no validamos el formato.
        if (phone.trim().isEmpty()) {
            return;
        }

        Matcher matcher = PHONE_PATTERN.matcher(phone);

        // Si el valor NO coincide con el patrón
        if (!matcher.matches()) {
            // Creamos un mensaje de error para JSF
            FacesMessage msg = new FacesMessage(
                    "Error de formato de teléfono.", // Título corto
                    "El teléfono debe tener el formato 7XXX-XXXX o 6XXX-XXXX."); // Detalle

            msg.setSeverity(FacesMessage.SEVERITY_ERROR); // Define el tipo de mensaje

            // Lanzamos la excepción para detener el proceso y mostrar el error
            throw new ValidatorException(msg);
        }
    }
}