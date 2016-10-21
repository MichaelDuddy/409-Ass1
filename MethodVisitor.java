package MethodVisiting;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * @author Michael Duddy
 * @since 20/10/2016
 * @version v0.1
 * 
 * Class that will be used for Accessing the Methods in our read class.
 * 
 * @param Uses the method declaration from JavaParser and our file that was read in the main.
 * 
 *  
 */
public class MethodVisitor extends VoidVisitorAdapter {

	
	/**
	 * This Method currently only obtains the method names.
	 * Here further operations occur.
	 */
    @Override
    public void visit(MethodDeclaration n, Object arg) {
        
        System.out.println(n.getName());
        super.visit(n, arg);
    }
}