package com.animationrecognition.model;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * tensorFlow lite for pokemon recognition
 */
public class PokemonTFlite {

    private static final String LOG_TAG = PokemonTFlite.class.getSimpleName();
    private static final String MODELPATH = "pokemon.tflite";
    private static final String LABELPATH = "labels.txt";
    private Interpreter mInterpreter;
    private List<String> labelList = new ArrayList<>();
    private final float[][] res = new float[1][149];

    private static final int INPUTX = 224;
    private static final int INPUTY = 224;
    private static final int BATCHSIZE = 1;
    private static final int PIXELSIZE = 3;
    private static final int IMAGEMEAN = 128;
    private static final float IMAGESTD = 128.0f;

    /**
     * constructor
     */
    public PokemonTFlite() {
        // It is a constructor for the class
    }

    /**
     * method to creat a new classifier
     *
     * @param assetManager object of assertManager
     * @throws IOException the exception of I/O
     */
    public void create(AssetManager assetManager) throws IOException {
        PokemonTFlite classifier = new PokemonTFlite();
        mInterpreter = new Interpreter(classifier.loadModelFile(assetManager));
        labelList = classifier.loadLabelList(assetManager);
    }

    /**
     * method to run the neural network
     *
     * @param bitmap object of a bitmap
     * @return the maximum probability for the pokemon recognition
     */
    public String run(Bitmap bitmap) {
        ByteBuffer input = convertBitmapToByteBuffer(bitmap);
        mInterpreter.run(input, res);
        return getMax(res);
    }

    private MappedByteBuffer loadModelFile(AssetManager assetManager) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(MODELPATH);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private List<String> loadLabelList(AssetManager assetManager) throws IOException {
        List<String> labelList = new ArrayList<>();
        InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open(LABELPATH), "UTF-8");
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = reader.readLine()) != null) {
            labelList.add(line);
        }
        reader.close();
        return labelList;
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * BATCHSIZE * INPUTX * INPUTY * PIXELSIZE);
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[INPUTX * INPUTY];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int pixel = 0;
        for (int i = 0; i < INPUTX; ++i) {
            for (int j = 0; j < INPUTX; ++j) {
                final int val = intValues[pixel++];
                byteBuffer.putFloat((((val >> 16) & 0xFF)-IMAGEMEAN)/IMAGESTD);
                byteBuffer.putFloat((((val >> 8) & 0xFF)-IMAGEMEAN)/IMAGESTD);
                byteBuffer.putFloat((((val) & 0xFF)-IMAGEMEAN)/IMAGESTD);
            }
        }
        return byteBuffer;
    }


    private String getMax(float[]... results) {
        int maxID = 0;
        float maxValue = results[0][maxID];
        for (int i = 1; i < results[0].length; i++) {
            if (results[0][i] > maxValue) {
                maxID = i;
                maxValue = results[0][i];
            }
        }
        return labelList.get(maxID);
    }

}
