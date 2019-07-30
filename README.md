# SimplePlaceSearch
Simple place search using google place autocomplete suggestion API.

[![](https://jitpack.io/v/koushikcse/SimplePlaceSearch.svg)](https://jitpack.io/#koushikcse/SimplePlaceSearch)

## Add to Gradle

Add this to your project level `build.gradle` file

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

And then add this to your module level `build.gradle` file

```gradle
dependencies {
    implementation 'com.github.koushikcse:SimplePlaceSearch:V1.1'
}
```
### How it works
In your activity or fragment from where you need search place, on button click just initialize `SimplePlaceSerch`. Then you will get `SearchResult` in `PlaceSearchListener` callback. See bellow for details uses of this library.

##### Kotlin
```
btnSearchPlace.setOnClickListener {
            PlaceSearchWidget.initialize(this,
                "GOOGLE_PLACE_API_KEY",
                object : PlaceSearchWidget.PlaceSearchListener {
                    override fun successPlaceSearch(searchResult: SearchResult) {
                        //todo success finctionality
                    }

                    override fun failedPlaceSearch(error: String) {
                        //todo failed finctionality
                    }
                })
        }
```
##### Java
```
btnSearchPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceSearchWidget.Companion.initialize(this,
                "GOOGLE_PLACE_API_KEY"
                , new PlaceSearchWidget.PlaceSearchListener() {
                    @Override
                    public void successPlaceSearch(@NotNull SearchResult searchResult) {
                        //todo success finctionality
                    }

                    @Override
                    public void failedPlaceSearch(@NotNull String s) {
                        //todo failed finctionality
                    }
                });
            }
        });
```


# LICENSE

MIT License

Copyright (c) 2019 Koushik Mondal

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
