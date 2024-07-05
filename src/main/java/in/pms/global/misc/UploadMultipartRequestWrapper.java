/**********************************************************
 Project:	   DWH_TB
 File:         UploadMultipartRequestWrapper.java
 Created:      Aug 9, 2017
 Last Changed: Aug 9, 2017
 Author:       Shivam Bhatnagar

This code is copyright (c) 2017 C-DAC Noida.

 ***********************************************************/
package in.pms.global.misc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set; 

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
 
/**
 * Wrap multipart request in order to make uploaded files accessible 
 * to the {@link net.mymam.ui.Upload Upload} component. 
 
 * <p/> 
 * See section "How do I support file uploads" in <i>Core Java Server Faces</i>, 3rd Edition. 
 
 * <p/> 
 * TODO: Maybe replace with BalusC's servlet 3.0-based implementation, 
 * http://balusc.blogspot.de/2009/12/uploading-files-in-servlet-30.html 
 * 
 * @author fstab 
 */ 
public class UploadMultipartRequestWrapper extends HttpServletRequestWrapper { 
 
    private Map<String, List<String>> formParameters = new HashMap(); 
    private Map<String, List<FileItem>> fileParameters = new HashMap(); 
 
    public UploadMultipartRequestWrapper(HttpServletRequest request, ServletFileUpload upload) throws ServletException { 
        super(request); 
        try { 
            List<FileItem> fileItems = upload.parseRequest(request); 
            for (int i = 0; i < fileItems.size(); i++) { 
                FileItem item = (FileItem) fileItems.get(i); 
                if (item.isFormField()) { 
                    addParameter(item.getFieldName(), item.getString(), formParameters); 
                } else { 
                    addParameter(item.getFieldName(), item, fileParameters); 
                } 
            } 
        } catch (FileUploadException e) { 
            // Request timed out 
            ServletException servletException = new ServletException(); 
            servletException.initCause(e); 
            throw servletException; 
        } 
    } 
 
    // Add FileItems to fileParameters, and Strings to formParameters. 
    private <T> void addParameter(String key, T value, Map<String, List<T>> map) { 
        if (map.containsKey(key)) { 
            map.get(key).add(value); 
        } else { 
            List<T> values = new ArrayList<T>(); 
            values.add(value); 
            map.put(key, values); 
        } 
    } 
 
    @Override 
    public String getParameter(String name) { 
        if (formParameters.containsKey(name)) { 
            List<String> values = formParameters.get(name); 
            if (values.isEmpty()) { 
                return ""; 
            } 
            else { 
                return values.get(0); 
            } 
        } else { 
            return super.getParameter(name); 
        } 
    } 
 
    @Override 
    public Map getParameterMap() { 
        Map<String, String[]> map = new HashMap(); 
        for (String formParam : formParameters.keySet()) { 
            map.put(formParam, formParameters.get(formParam).toArray(new String[] {})); 
        } 
        map.putAll(super.getParameterMap()); 
        return Collections.unmodifiableMap(map); 
    } 
 
    @Override 
    public Enumeration getParameterNames() { 
        Set<String> paramNames = new LinkedHashSet<String>(); 
        paramNames.addAll(formParameters.keySet()); 
        Enumeration<String> original = super.getParameterNames(); 
        while (original.hasMoreElements()) { 
            paramNames.add(original.nextElement()); 
        } 
        return Collections.enumeration(paramNames); 
    } 
 
    @Override 
    public String[] getParameterValues(String name) { 
        if (formParameters.containsKey(name)) { 
            List<String> values = formParameters.get(name); 
            if (values.isEmpty()) { 
                return new String[] {}; 
            } 
            else { 
                return values.toArray(new String[values.size()]); 
            } 
        } else { 
            return super.getParameterValues(name); 
        } 
    } 
 
    /**
     * Get the file parameter from a multipart request. 
     * 
     * @param name the {@link net.mymam.ui.Upload Upload} component's client id. 
     * @return the {@link FileItem} representing the file in the request. 
     */ 
    public FileItem getFileItem(String name) { 
        if (fileParameters.containsKey(name)) { 
            List<FileItem> items = fileParameters.get(name); 
            // We assume that each request has only one file item. 
            // This is configurable in jQuery's file upload plugin with 'singleFileUploads=true'. 
            // One file per request is the default configuration in 'jquery.fileupload.js'. 
            if ( items.size() != 1 ) { 
                throw new UnsupportedOperationException("Cannot handle multiple files in a single request."); 
            } 
            return items.get(0); 
        } else { 
            return null; 
        } 
    } 
}