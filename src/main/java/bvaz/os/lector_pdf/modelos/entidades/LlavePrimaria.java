package bvaz.os.lector_pdf.modelos.entidades;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;

@Target(FIELD)
@Retention(RUNTIME)
public @interface LlavePrimaria {
	int orden() default 1;
	boolean autoincremento() default true;
}
