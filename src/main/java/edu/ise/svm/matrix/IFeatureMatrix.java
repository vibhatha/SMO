package edu.ise.svm.matrix;

/**
 * Created by vlabeyko on 9/28/2016.
 */
public interface IFeatureMatrix {

    public FeatureMatrix generate();

    public void print();

    public FeatureMatrix add();

    public FeatureMatrix subtract();

    public FeatureMatrix product(String productType); //dot product or cross product

    public FeatureMatrix transpose(FeatureMatrix featureMatrix);


}
