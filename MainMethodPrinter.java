package Main;


import java.io.FileInputStream;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import MethodVisiting.MethodVisitor;

/**
 * @author Michael Duddy
 * @since 20/10/2016
 * @version v0.1
 * 
 * The class below is used for reading in my java files.(currently HardCoded)
 * I then use java parser and the visitor pattern to obtain each of the methods in
 * the class that was read in.
 * 
 **/


public class MainMethodPrinter {

    public static void main(String[] args) throws Exception {
        // creates an input stream for the file to be parsed
        FileInputStream in = new FileInputStream("./Countdown.java");

        CompilationUnit cu;
        try {
            // parse the file
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }

        // visit and print the methods names
        new MethodVisitor().visit(cu, null);
    }

 
}