package ru.kolesnikovdmitry.lesson6menu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    TextView mTextViewMenuItemSelected;
    String mStringText  = ""; //наши переменные строки
    String mStringAbout = "";
    View mainView; //вьюшка для снекбара, обьявим ее в методе onCreate().
    Button mButtonNothing;
    public static final int RED_MENU_ITEM_ID         = 101;    //это идентификаторы 101-103 для субменю где выбираются цвета
    public static final int GROUP_COLOR_MENU_ITEM_ID = 100;    //это идентификатор всей группы переключателей цвета
    public static final int GREEN_MENU_ITEM_ID       = 102;
    public static final int DEFAULT_MENU_ITEM_ID     = 103;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonNothing = findViewById(R.id.buttonNothingActivityMain); //это мы берем вьюшку для снекбара одну раз и навсегда
        mainView = mButtonNothing;  //блять оказывается можно просто присваивать обьекту класса View объект другого класса, например Button

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();  //вызываем обьект этого класса, чтобы задать наше меню
        inflater.inflate(R.menu.menu_main, menu);   //задаем наше меню
        SubMenu subMenuColor = menu.addSubMenu("цвет текста");  //можно добавить субменюшку программно, что мы и сделали
        subMenuColor.add(GROUP_COLOR_MENU_ITEM_ID, RED_MENU_ITEM_ID,     Menu.NONE, "red");   //первый параметр - айди группы, 2 - айди пункта, 3й- хзб 4й - титульник
        subMenuColor.add(GROUP_COLOR_MENU_ITEM_ID, GREEN_MENU_ITEM_ID,   Menu.NONE, "green");
        subMenuColor.add(GROUP_COLOR_MENU_ITEM_ID, DEFAULT_MENU_ITEM_ID, Menu.NONE, "default");
        subMenuColor.setGroupCheckable(GROUP_COLOR_MENU_ITEM_ID, true, true); //этим методом присваиваем группе менюшек переключатель: 1й параметр - айди группы
        //2й - возможность проверять элементы и вообще менять состояние их, 3й - тру - выбираем только один тз группы, фолс - можно просто ставить галочки типо да или нет независимо друг от друга.
        return true;
    }

    public void onClickMenuItemActivityMain(MenuItem item) {
        mTextViewMenuItemSelected = findViewById(R.id.textViewMenuItemActivityMain);
        switch (item.getItemId()) {
            case R.id.menuAboutActivityMain:
                if(!item.isChecked()) { //проверяем, стоит ли галочка
                    mStringAbout = getResources().getString(R.string.menu_item_about_activity_main);
                    mTextViewMenuItemSelected.setText(mStringAbout + " " + mStringText);
                }
                else {
                    mStringAbout = "";
                    mTextViewMenuItemSelected.setText(mStringAbout + mStringText);
                }
                item.setChecked(!item.isChecked()); //при каждом нажатии галочка будет пропадать или появляться
                break;
            case R.id.menuAlinaActivityMain:
                mStringText = getResources().getString(R.string.menu_item_alina_activity_main);
                mTextViewMenuItemSelected.setText(mStringAbout + " " + mStringText);
                item.setChecked(!item.isChecked()); //а тут галочка не исчезнет, если повторно нажать на этот айтем, только если программно или выбрать другую
                break;
            case R.id.menuColorActivityMain:
                Snackbar snackbarColor = Snackbar.make(mainView, "Coming soon...", Snackbar.LENGTH_LONG);
                snackbarColor.show();
                break;
            case R.id.menu_item_search_activity_main:
                mStringText = getResources().getString(R.string.menu_item_search_activity_main); //аналогично пункту Алина
                mTextViewMenuItemSelected.setText(mStringAbout + " " + mStringText);
                item.setChecked(!item.isChecked());
                break;
            case R.id.menuSettingsActivityMain:
                Snackbar snackbarSettings = Snackbar.make(mainView, "This is me NONO zone!", Snackbar.LENGTH_LONG);
                snackbarSettings.show();
                break;
            default:
                break;
        }
    }


    //элементы подменю работают только с методом onOpionItemSelected, зато можно не искать их айди через R.id.... а просто воспользоваться
    //идентификатором, который мы сами сначала присвоили (В нашем случае RED_MENU_ITEM_ID и GREEN_MENU_ITEM_ID)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mTextViewMenuItemSelected = findViewById(R.id.textViewMenuItemActivityMain);
        switch (item.getItemId()) {
            case RED_MENU_ITEM_ID:
                Toast.makeText(getApplicationContext(), "red",   Toast.LENGTH_SHORT).show();
                item.setChecked(!item.isCheckable()); //если из группы менюшек надо выбрать одну, то после выбора она не убирается.
                mTextViewMenuItemSelected.setTextColor(getResources().getColor(R.color.red));
                return true;
            case GREEN_MENU_ITEM_ID:
                Toast.makeText(getApplicationContext(), "green", Toast.LENGTH_SHORT).show();
                mTextViewMenuItemSelected.setTextColor(getResources().getColor(R.color.green));
                item.setChecked(!item.isChecked());
                return true;
            case DEFAULT_MENU_ITEM_ID:
                //опять же не забываем, что если элемент mainView будет находиться не в CoordinatorLayout, то не будет работать атрибут app:layout_dodgeInsetEdges="bottom"
                //и снекбар будет загораживать нижние элементы экрана, когда он будет выежать. Но вообще достаточно, чтобы корневым элементом просто был CoordinatorLayout
                //а в него уже просто вставлять все остальные лейауты, например ConstraintLayout.
                final Snackbar snackbar = Snackbar.make(mainView, "DEFAULT TEXT COLOR", Snackbar.LENGTH_SHORT);
                Toast.makeText(getApplicationContext(), mainView.toString(), Toast.LENGTH_LONG).show();
                snackbar.setDuration(10000); //длительность снекбара в мили секундах
                snackbar.setAction("ok", new View.OnClickListener() { //а вот так можно прикрепить кнопочку программно в снекбар
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss(); //просто убираем снекбар
                    }
                });
                snackbar.show();
                mTextViewMenuItemSelected.setTextColor(getResources().getColor(R.color.design_default_color_primary_dark));
                item.setChecked(!item.isChecked());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //обрабатываем нажатие на кнопку единственную нашу
    public void onClickButtonMainActivity(View view) {
        switch (view.getId()) {
            case R.id.buttonNothingActivityMain:
                //здесь можно не париться и сразу присвоить первому аргументу переменнуб view, ибо он сразу дана
                Snackbar snackbarNothing = Snackbar.make(view, "NOTHING DONE", Snackbar.LENGTH_SHORT);
                snackbarNothing.show();
                Toast.makeText(getApplicationContext(), view.toString(), Toast.LENGTH_LONG).show(); //как выглядит эта переменная в виде строки
                break;
            default:
                break;
        }
    }
}
