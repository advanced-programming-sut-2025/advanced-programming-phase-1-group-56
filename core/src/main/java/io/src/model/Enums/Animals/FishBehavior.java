package io.src.model.Enums.Animals;

// io.src.view.GameMenus.fishing.FishBehavior.java
//package io.src.view.GameMenus.fishing;

import com.badlogic.gdx.math.MathUtils;

public enum FishBehavior {
    MIXED {
        @Override
        public float nextTarget(float t) {
            // هر لحظه ترکیبی از موج و تصادفی
            return (float)(Math.sin(t*2.0)*0.25 + MathUtils.random(-0.15f,0.15f));
        }
    },
    SMOOTH {
        @Override
        public float nextTarget(float t) {
            return (float)Math.sin(t*1.5) * 0.2f;
        }
    },
    SINKER {
        @Override
        public float nextTarget(float t) {
            return -0.25f + (float)Math.sin(t*1.2)*0.05f; // تمایل نزولی
        }
    },
    FLOATER {
        @Override
        public float nextTarget(float t) {
            return +0.25f + (float)Math.sin(t*1.2)*0.05f; // تمایل صعودی
        }
    },
    DART {
        @Override
        public float nextTarget(float t) {
            // بیشتر اوقات آرام، گاهی جهش تند
            if (MathUtils.random() < 0.03f) return MathUtils.random(-0.6f, 0.6f);
            return (float)Math.sin(t*1.2)*0.08f;
        }
    };

    /**
     * مقدار هدفی که fish به سمت آن می‌رود؛ مقدار در بازه approx [-1,1] برگردد.
     * پارامتر t زمان تجمعی است که fish درگیر است (برای تولید موج).
     */
    public abstract float nextTarget(float t);
}

