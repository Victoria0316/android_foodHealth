package com.cow205.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
	private ArrayList<Fragment> fragments;
	private FragmentManager fm;

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
		this.fm = fm;
	}

	public FragmentAdapter(FragmentManager fragmentManager,
			ArrayList<Fragment> fragments) {
		super(fragmentManager);
		this.fm = fragmentManager;
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}
}
