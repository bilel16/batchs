package com.bna.smile.web.souscription.servlets;

import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Signature;

import com.bna.commun.model.SignatureId;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.SignaturePersCpt;


import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertSignaturesTrt;
import com.bna.smile.web.souscription.forms.SignaturesContratCompteForm;

import com.oxia.fwk.context.Context;

import java.awt.image.BufferedImage;

import java.io.*;

import java.util.*;

import javax.imageio.ImageIO;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;

import java.sql.Blob;



public class ShowImageFr extends HttpServlet {
    //Initialize global variables 

    protected void service(HttpServletRequest request, 
                           HttpServletResponse response) throws ServletException, 
                                                                IOException {
        ServletOutputStream out = null;
        try {
            
            // SignaturesContratCompteForm signaturesContratCompteForm = (SignaturesContratCompteForm)request.getSession().getAttribute("signaturesContratCompteForm");
            // BufferedImage biFr = signaturesContratCompteForm.getBufferedImageFr();
            BufferedImage biFr = (BufferedImage)  request.getSession().getAttribute("bufferedImageFr");
            if (biFr != null) {
                response.setContentType("image/jpeg");
                // send the image straight to the client
                out = response.getOutputStream();
                ImageIO.write(biFr, "JPEG", out);
                // flush/close resources
                out.flush();
                out.close();
            }

        } catch (Exception e) {
            
            e.printStackTrace();
        }
        finally{
            if(out!=null) out.close();
        }
    }
}
