package in.codesquad.bepositive;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.MaskFilterSpan;
import android.text.style.TypefaceSpan;
import android.widget.Button;
import android.widget.Toast;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

/**
 * Created by SREE on 09/10/2016.
 */
public class MainIntroActivity extends IntroActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setButtonBackVisible(false);
        setButtonNextVisible(false);
        setButtonCtaVisible(true);
        setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_BACKGROUND);
        TypefaceSpan labelSpan = new TypefaceSpan(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? "sans-serif-medium" : "sans serif");
        SpannableString label = SpannableString.valueOf(getString(R.string.label_button_cta_canteen_intro));
        label.setSpan(labelSpan, 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setButtonCtaLabel(label);



        setPageScrollDuration(500);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setPageScrollInterpolator(android.R.interpolator.fast_out_slow_in);
        }

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_canteen_intro1)
                .description(R.string.description_canteen_intro1)
                .image(R.drawable.bepositive)
                .background(R.color.color_canteen)
                .backgroundDark(R.color.color_dark_canteen)
                .layout(R.layout.slide_canteen)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_canteen_intro2)
                .description(R.string.description_canteen_intro2)
                .image(R.drawable.blood1)
                .background(R.color.color_canteen)
                .backgroundDark(R.color.color_dark_canteen)
                .layout(R.layout.slide_canteen)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_canteen_intro3)
                .description(R.string.description_canteen_intro3)
                .image(R.drawable.blood2)
                .background(R.color.color_canteen)
                .backgroundDark(R.color.color_dark_canteen)
                .layout(R.layout.slide_canteen)
                .build());

        autoplay(2500, INFINITE);
    }

    private void setButtonCtaClickListener() {
        Toast.makeText(MainIntroActivity.this, "clicked ", Toast.LENGTH_SHORT).show();
    }
}