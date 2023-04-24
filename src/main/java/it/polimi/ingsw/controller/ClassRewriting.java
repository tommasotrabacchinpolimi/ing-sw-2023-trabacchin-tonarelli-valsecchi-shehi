package it.polimi.ingsw.controller;

import java.io.IOException;

public class ClassRewriting {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        getThrowingClass(ClientInterface.class);
    }

    public static Class<?> getThrowingClass(Class<?> tClass) throws ClassNotFoundException {
        return Class.forName(tClass.getPackageName() + ".rmiInterfaces." + "Remote" + tClass.getSimpleName());
    }
    /*public static Class<?> getThrowingClass(Class<?> tClass) throws IOException, ClassNotFoundException {
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {new File("src/main/java/").toURI().toURL()});
        try{
            return urlClassLoader.loadClass(tClass.getPackageName() + ".generated." + "Remote"+tClass.getSimpleName());
        }catch(ClassNotFoundException ex) {
            String output = "";
            if (!tClass.isInterface()) {
                return null;
            }

            output += "package " + tClass.getPackageName() + ".generated;\n";
            output += "import java.rmi.RemoteException;\n";
            output += "import java.rmi.Remote;\n";
            output += "import it.polimi.ingsw.net.RemoteInterface;\n";
            output += "public interface Remote" + tClass.getSimpleName() + " extends RemoteInterface {\n";
            for(Method method : tClass.getMethods()) {
                if(!Modifier.isPublic(method.getModifiers()) || !method.getReturnType().equals(Void.TYPE)) {
                    return null;
                }
                output += "public void " + method.getName() + "(";
                int cont = 0;
                for(Class<?> type : method.getParameterTypes()) {
                    output += type.getCanonicalName() + " p"+cont + ", ";
                    cont++;
                }
                if(cont != 0) {
                    output = output.substring(0, output.length() - 2);
                }
                output += ") throws RemoteException ;\n";
            }
            output += "}\n";
            System.out.println(output);
            File newFile = new File("src/main/java/it/polimi/ingsw/controller/generated/Remote" + tClass.getSimpleName() + ".java");
            newFile.createNewFile();
            FileWriter fileWriter = new FileWriter(newFile);
            fileWriter.write(output);
            fileWriter.close();
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(
                    null, null, null);

            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays
                    .asList(new File("src/main/java/")));
            boolean success = compiler.getTask(null, fileManager, null, null, null,
                            fileManager.getJavaFileObjectsFromFiles(Arrays.asList(newFile)))
                    .call();
            fileManager.close();
            //RMIClassLoader.loadClass(new File("src/main/java/").toURI().toURL(), tClass.getPackageName() + ".generated." + "Remote"+tClass.getSimpleName());
            return urlClassLoader.loadClass(tClass.getPackageName() + ".generated." + "Remote"+tClass.getSimpleName());
        }

    }*/

}
