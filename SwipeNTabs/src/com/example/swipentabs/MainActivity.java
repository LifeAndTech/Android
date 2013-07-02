/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.swipentabs;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener
{
	// Declare the PagerAdapter and ViewPager that will be used
	CollectionPagerAdapter myCollectionPagerAdapter;
	ViewPager myViewPager;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Create an adapter that when requested, will return a fragment
		// representing an object in the collection.
		//
		// ViewPager and its adapters use Support Library Fragment,
		// so we use getSupportFragmentManager.
		myCollectionPagerAdapter = new CollectionPagerAdapter(getSupportFragmentManager());
		
		// Setup the action bar.
		final ActionBar actionBar = getActionBar();
		
		// Specify that the Home/Up button should not be enabled, since there is
		// no hierarchical parent.
		actionBar.setHomeButtonEnabled(false);
		
		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// Setup the ViewPager, attaching the adapter and setting up a listener
		// for when the user swipes between sections
		myViewPager = (ViewPager)findViewById(R.id.pager); // locate the object
		myViewPager.setAdapter(myCollectionPagerAdapter); // set adapter to object
		
		// Setup listener on page changes
		myViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
		{
			public void onPageSelected(int position)
			{
				// When swiping between different app sections,
				// select the corresponding tab.
				actionBar.setSelectedNavigationItem(position);
			}
		});
		
		// For each of the sections in the app, add a tab to the action bar
		for(int i = 0; i < myCollectionPagerAdapter.getCount(); i++)
		{
			// Create a tab with text corresponding to the page title defined by
			// the adapter
			// Also specify this Activity object, which implements the
			// TabListener interface, as the listener for when this tab is selected
			actionBar.addTab(actionBar.newTab().setText(myCollectionPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {}

	public void onTabSelected(Tab tab, FragmentTransaction ft) 
	{
		// When the given tab is selected, switch to the corresponding page in the ViewPager
		myViewPager.setCurrentItem(tab.getPosition());
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {}
	
	// Create the CollectionPagerAdapter class that we declare previously
	// Since this is an object collection, use FragmentStatePagerAdapter
	// and NOT a FragmentPagerAdapter
	public class CollectionPagerAdapter extends FragmentStatePagerAdapter
	{
		// Declare the number of tabs
		final int NUM_TABS = 3;
		
		// Constructor for the PagerAdapter
		public CollectionPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}
		
		// Create a method to retrieve the fragment(s)
		public Fragment getItem(int i)
		{
			// Declare a new tab fragment
			Fragment fragment = new TabFragment();
			
			// Form the arguments
			Bundle args = new Bundle();
			
			// In the arguments, put int value to replace any existing value 
			// of the given key.
			args.putInt(TabFragment.ARG_OBJECT, i);
			
			// Apply the arguments to the fragment
			fragment.setArguments(args);
			return fragment;
		}

		// Implement the required abstract method
		public int getCount() 
		{
			return NUM_TABS;
		}
		
		// Create a method to get the page/tab title
		public CharSequence getPageTitle(int position)
		{
			String tabLabel = null; // null by default
			switch(position)
			{
			case 0:
				tabLabel = getString(R.string.tab1_label);
				break;
			case 1:
				tabLabel = getString(R.string.tab2_label);
				break;
			case 2:
				tabLabel = getString(R.string.tab3_label);
				break;
			}
			
			return tabLabel;
		}
	}
	
	// Create a the TabFragment declared previously by extending Fragment class
	public static class TabFragment extends Fragment
	{
		public static final String ARG_OBJECT = "object";
		
		// Inflate the content of each fragment
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			Bundle args = getArguments();
			int position = args.getInt(ARG_OBJECT);
			
			int tabLayout = 0; // default value
			switch(position)
			{
			case 0:
				tabLayout = R.layout.tab1;
				break;
			case 1:
				tabLayout = R.layout.tab2;
				break;
			case 2:
				tabLayout = R.layout.tab3;
				break;
			}
			
			// Inflating the XML
			View rootView = inflater.inflate(tabLayout, container, false);
			return rootView;
		}
	}

}
