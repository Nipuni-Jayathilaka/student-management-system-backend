package lk.ijse.dep9.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.persistence.Tuple;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lk.ijse.dep9.dto.StudentDTO;
import lk.ijse.dep9.entity.Student;
import lk.ijse.dep9.util.HibernateUtil;
import lk.ijse.dep9.util.HttpServlet2;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@MultipartConfig
@WebServlet(urlPatterns = "/students", name = "StudentServlet",loadOnStartup = 1)
public class StudentServlet extends HttpServlet2 {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin","*");
        System.out.println("nipuni");
        try (Session session =HibernateUtil.getSessionFactory().openSession()){
            System.out.println("1");

//
            NativeQuery<Tuple> nativeQuery = session.createNativeQuery("SELECT * FROM Student s", Tuple.class);
            List<StudentDTO> list=new ArrayList<>();
            List<Tuple> stream = nativeQuery.list();
            for(Tuple tuple: stream){
                list.add(new StudentDTO((Integer) tuple.get("id"),(String) tuple.get("name"),(String)tuple.get("address"),(String)tuple.get("contact")));
                System.out.printf("%s, %s, %s, %s ",tuple.get("id"),tuple.get("name"),tuple.get("address"),tuple.get("contact"));

            };
            Jsonb jsonb = JsonbBuilder.create();
            resp.setContentType("application/json");
            jsonb.toJson(list, resp.getWriter());

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin","*");

        String name=req.getParameter("name");
        String address=req.getParameter("address");
        String contact=req.getParameter("contact");
        Part profilePic=req.getPart("profile-picture");

        try(Session session = HibernateUtil.getSessionFactory().openSession();){
            session.beginTransaction();

            Student student=new Student(name, address, contact);
            session.persist(student);
            if(profilePic!=null) {
                Path picturePath = Path.of(getServletContext().getRealPath("/"), "pictures",student.getId()+"");
                profilePic.write(picturePath.toString());
                student.setPicture(picturePath.toString());
            }
            resp.setStatus(HttpServletResponse.SC_CREATED);

            session.getTransaction().commit();
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
