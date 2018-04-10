package ztml.dev.ngokhacbac.examplefile;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private String fileName = "txtfiletest.txt";
    private String filePath = "DCIM";
    private EditText mEditTextContent;
    private Button mButtonSave;
    private Button mButtonRead;
    private Button mButtonSaveSD;
    private Button mButtonReadSD;
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditTextContent = findViewById(R.id.edtContent);
        mButtonRead = findViewById(R.id.btnRead);
        mButtonSave = findViewById(R.id.btnWrite);
        mButtonReadSD = findViewById(R.id.btnReadSD);
        mButtonSaveSD = findViewById(R.id.btnWriteSD);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeData(fileName, mEditTextContent.getText().toString());

            }
        });

        mButtonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData(fileName);
            }
        });
        /*****/
        mButtonSaveSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeDataSD(fileName, filePath, mEditTextContent.getText().toString());
            }
        });
        mButtonReadSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readDataSD(fileName,filePath);
            }
        });
    }

    public void writeDataSD(String fileName, String filePath, String content) {
        try {

            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath() + filePath);
            directory.mkdirs();
            File file = new File(directory, fileName);
            FileOutputStream fOut = new FileOutputStream(file);

            OutputStreamWriter writer = new OutputStreamWriter(fOut);
            writer.write(content);
            writer.flush();
            writer.close();
            Toast.makeText(this, "Save success !", Toast.LENGTH_SHORT).show();
            mEditTextContent.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void writeData(String fileName, String content) {
        try {
            FileOutputStream os = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(os);
            writer.write(content);
            writer.flush();
            writer.close();
            Toast.makeText(this, "Save success !", Toast.LENGTH_SHORT).show();
            mEditTextContent.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readData(String filename) {
        try {
            FileInputStream ip = openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(ip);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;
            while ((charRead = inputStreamReader.read(inputBuffer)) > 0) {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                s += readString;
                inputBuffer = new char[READ_BLOCK_SIZE];
            }
            mEditTextContent.setText(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDataSD(String filename, String filePath) {
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath() + filePath);
            File file = new File(directory, filename);
            FileInputStream fIn = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;
            while ((charRead = isr.read(inputBuffer)) > 0) {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                s += readString;
                inputBuffer = new char[READ_BLOCK_SIZE];
            }
            mEditTextContent.setText(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
