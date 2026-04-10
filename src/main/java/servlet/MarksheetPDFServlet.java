package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.List;

import dao.StudentDAO;
import model.Student;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@WebServlet("/marksheet-pdf")
public class MarksheetPDFServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Student student = (Student) session.getAttribute("studentData");

        if (student == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        StudentDAO dao = new StudentDAO();
        List<String[]> marks = dao.getMarksheet(student.getId());   // ✅ FIXED

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=marksheet.pdf");

        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, response.getOutputStream());
            doc.open();

            doc.add(new Paragraph("STUDENT MARKSHEET"));
            doc.add(new Paragraph("Name: " + student.getName()));
            doc.add(new Paragraph("Email: " + student.getEmail()));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.addCell("Subject");
            table.addCell("Marks");
            table.addCell("Grade");

            int total = 0;
            for (String[] row : marks) {
                table.addCell(row[0]);
                table.addCell(row[1]);
                table.addCell(row[2]);
                total += Integer.parseInt(row[1]);
            }

            doc.add(table);

            double percent = marks.size() == 0 ? 0 : (double) total / marks.size();
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Percentage: " + String.format("%.2f", percent)));
            doc.add(new Paragraph("Result: " + (percent >= 40 ? "PASS" : "FAIL")));

            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
