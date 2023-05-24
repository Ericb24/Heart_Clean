import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import java.util.Locale;
import java.util.Scanner;
public class Heart_Back {
    private Instances data;
    private Classifier classifier;
    private Scanner scanner;

    public Heart_Back(){
        scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        try {
            DataSource source = new DataSource("C:\\Users\\ASUS\\Documents\\Archivos ARFF\\Heart Clean.arff");
            data = source.getDataSet();
            data.setClassIndex(data.numAttributes() - 1);

            //Convertir el atributo clase a nominal para poder usar el j48
            NumericToNominal filter = new NumericToNominal();
            filter.setInputFormat(data);
            data = Filter.useFilter(data, filter);

            classifier = new J48();
            classifier.buildClassifier(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void predictInConsole(){
        //Obtener los valores ingresados por el usuario
        double edad = promptDoubleInput("Age: ");
        double genero = promptDoubleInput("Sex (Male = 0 Female = 1): ");
        double chest_pain = promptDoubleInput("Chest Pain Type (ASY = 0 ATA = 1 NAP = 2 TA = 3): ");
        double pressure = promptDoubleInput("Resting Blood Pressure: ");
        double serum = promptDoubleInput("Cholesterol: ");
        double sugar = promptDoubleInput("Fasting Blood Sugar: ");
        double electro = promptDoubleInput("Resting Electrocardiographic results (Normal = 0 ST = 1 LVH = 2): ");
        double max = promptDoubleInput("Maximum Heart Rate Achieved: ");
        double exer = promptDoubleInput("Excercise Induced Angina (No = 0 Yes = 1): ");
        double old = promptDoubleInput("Oldpeak ST: ");
        double slop = promptDoubleInput("The Slope of the Peak Exercise ST Segment (Down = 0 Flat = 1 Up = 2): ");

        Instance newInstance = new DenseInstance(12);
        newInstance.setValue(data.attribute("Age"), edad);
        newInstance.setValue(data.attribute("Sex"), genero);
        newInstance.setValue(data.attribute("ChestPainType"), chest_pain);
        newInstance.setValue(data.attribute("RestingBP"), pressure);
        newInstance.setValue(data.attribute("Cholesterol"), serum);
        newInstance.setValue(data.attribute("FastingBS"), sugar);
        newInstance.setValue(data.attribute("Re1ingECG"), electro);
        newInstance.setValue(data.attribute("MaxHR"), max);
        newInstance.setValue(data.attribute("ExerciseA0gi0a"), exer);
        newInstance.setValue(data.attribute("Oldpeak"), old);
        newInstance.setValue(data.attribute("ST_Slope"), slop);
        newInstance.setDataset(data);

        try {
            //Realizar la predicción
            double predictedClass = classifier.classifyInstance(newInstance);
            String predictedClassName = data.classAttribute().value((int) predictedClass);

            //Mostrar el resultado de la predicción en la consola
            System.out.println("El diagnostico predicho es: "+predictedClassName);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private double promptDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Entrada inválida. Introduce un número válido.");
            System.out.print(prompt);
            scanner.next();
        }
        return scanner.nextDouble();
    }
}
