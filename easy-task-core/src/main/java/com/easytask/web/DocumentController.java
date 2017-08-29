package com.easytask.web;

import com.easytask.model.jpa.Document;
import com.easytask.model.jpa.Project;
import com.easytask.model.jpa.User;
import com.easytask.service.ICommentService;
import com.easytask.service.IDocumentService;
import com.easytask.service.IProjectService;
import com.easytask.service.IUserService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.Valid;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Marijo on 19-Aug-17.
 */
@RestController
@RequestMapping(value = "/api/document", produces = "application/json")
public class DocumentController {

    List<String> imageFormats = Arrays.asList("png","jpg","jpeg");
    List<String> textFormats = Arrays.asList("txt");
    List<String> zipFormats = Arrays.asList("zip","rar","7z");
    List<String> codeFormats = Arrays.asList("c","cpp", "py", "java", "sql", "cs");

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        IDocumentService documentServiceBean = applicationContext.getBean(IDocumentService.class);
    }

    private IDocumentService documentService;
    private IUserService userService;
    private IProjectService projectService;

    @Autowired
    DocumentController(IDocumentService documentService, IUserService userService, IProjectService projectService) {
        this.documentService = documentService;
        this.userService = userService;
        this.projectService = projectService;
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public Document save(HttpServletRequest request){

        MultipartHttpServletRequest mRequest;
        Document document = new Document();

        try {
            mRequest =  (MultipartHttpServletRequest) request;

            Project project = projectService.findById(Long.parseLong(mRequest.getParameter("project")));
            User user = userService.findById(Long.parseLong(mRequest.getParameter("user")));

            document.setUser(user);
            document.setProject(project);
            document.setDate(DateTime.now());

            Iterator itr = mRequest.getFileNames();

            MultipartFile mFile = mRequest.getFile((String)itr.next());
            String fileName = mFile.getOriginalFilename();
            document.setName(fileName);
            document.setSize(mFile.getSize());

            document.setIcon(formatFilter(fileName));

            Blob blob = new SerialBlob(mFile.getBytes());
            document.setData(blob);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return documentService.insert(document);
    }

    private String formatFilter(String name) {
        String[] nameArray = name.split("\\.");
        String extension = nameArray[nameArray.length-1].toLowerCase();
        System.out.println("eeeeeeeeeeeextension"+extension);
        if (extension.equals("pdf")) return "-pdf-o";
        else if(extension.equals("doc") || extension.equals("docx")) return "-word-o";
        else if(extension.equals("xlsx") || extension.equals("xls")) return "-excel-o";
        else if(extension.equals("pptx") || extension.equals("ppt")) return "-powerpoint-o";
        else if (textFormats.contains(extension)) return "-text-o";
        else if (zipFormats.contains(extension)) return "-zip-o";
        else if(imageFormats.contains(extension)) return "-image-o";
        else if(codeFormats.contains(extension)) return "-code-o";

        return " ";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void downloadDocument(@PathVariable Long id,HttpServletResponse response) {
        Document doc = documentService.findById(id);
        try {
            response.setHeader("Content-Disposition", "inline;filename=\"" +doc.getName()+ "\"");
            OutputStream out = response.getOutputStream();
            response.setContentType("multipart/form-data");
            IOUtils.copy(doc.getData().getBinaryStream(), out);
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        documentService.deleteById(id);
    }

}
