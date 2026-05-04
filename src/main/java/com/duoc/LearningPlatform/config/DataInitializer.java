package com.duoc.LearningPlatform.config;

import com.duoc.LearningPlatform.model.Course;
import com.duoc.LearningPlatform.model.User;
import com.duoc.LearningPlatform.model.enums.Role;
import com.duoc.LearningPlatform.repository.CourseRepository;
import com.duoc.LearningPlatform.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Carga datos de muestra en la base de datos H2 al iniciar la aplicación.
 * Garantiza que haya cursos disponibles para consultar el catálogo activo.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public DataInitializer(UserRepository userRepository,
                           CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) {
        // Si ya hay datos, no reinsertar (evita duplicados con base de datos persistente como Oracle)
        if (userRepository.count() > 0) {
            System.out.println(">>> DataInitializer: datos ya existentes, se omite la carga inicial.");
            return;
        }

        // Docente de ejemplo
        User teacher = new User("María González", "mgonzalez@duoc.cl", "secret123", Role.TEACHER);
        teacher = userRepository.save(teacher);

        // Cursos activos — títulos desordenados a propósito para mostrar el sort
        Course c1 = new Course();
        c1.setTitle("Desarrollo Web Full Stack");
        c1.setDescription("Aprende HTML, CSS, JavaScript y frameworks modernos para crear aplicaciones web completas.");
        c1.setTeacher(teacher);
        c1.setActive(true);

        Course c2 = new Course();
        c2.setTitle("Algoritmia y Programación");
        c2.setDescription("Fundamentos de algoritmos, estructuras de datos y resolución de problemas computacionales.");
        c2.setTeacher(teacher);
        c2.setActive(true);

        Course c3 = new Course();
        c3.setTitle("Base de Datos Relacionales");
        c3.setDescription("Modelado entidad-relación, SQL avanzado y optimización de consultas con PostgreSQL.");
        c3.setTeacher(teacher);
        c3.setActive(true);

        Course c4 = new Course();
        c4.setTitle("Matemáticas para Ingeniería");
        c4.setDescription("Cálculo diferencial e integral, álgebra lineal y probabilidades aplicadas.");
        c4.setTeacher(teacher);
        c4.setActive(true);

        Course c5 = new Course();
        c5.setTitle("Seguridad en Aplicaciones Web");
        c5.setDescription("OWASP Top 10, autenticación, autorización y protección contra vulnerabilidades comunes.");
        c5.setTeacher(teacher);
        c5.setActive(true);

        // Curso INACTIVO — NO debe aparecer en el catálogo de cursos activos
        Course c6 = new Course();
        c6.setTitle("Arquitectura de Microservicios");
        c6.setDescription("Diseño de sistemas distribuidos usando microservicios, Docker y Kubernetes.");
        c6.setTeacher(teacher);
        c6.setActive(false);

        courseRepository.save(c1);
        courseRepository.save(c2);
        courseRepository.save(c3);
        courseRepository.save(c4);
        courseRepository.save(c5);
        courseRepository.save(c6);

        System.out.println(">>> DataInitializer: datos de muestra cargados correctamente.");
        System.out.println(">>> Cursos activos: 5 | Cursos inactivos: 1");
    }
}
