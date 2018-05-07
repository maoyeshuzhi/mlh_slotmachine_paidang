package com.maoye.mlh_slotmachine.widget.banner.holder;

import android.view.View;

import com.bigkoo.convenientbanner.holder.*;


public interface CBViewHolderCreator {
	com.bigkoo.convenientbanner.holder.Holder createHolder(View itemView);
	int getLayoutId();
}
