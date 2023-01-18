# CryptoMarkets

## Features
This app is retrieving crypto market data.

It has 2 screens. The main `MarketScreen` is displaying the latest prices for the tokens available from 
the `CoinGecko` api. 
The `CoinDetailsScreen` can be accessed by tapping one of the tokens on the `MarketScreen`.

The features share commonalities:
- database first remote api access with a repository
- pull-to-refresh implemented
- snackbar with Refresh option on errors
- 'shimmer' screen before any data can be displayed

The link in the CoinDetailsScreen is clickable. 

Load data | Market data | Coin Details | Error                                  
:--:|:--:|:--:|:--:
| ![](https://raw.githubusercontent.com/ayonymus/Challenge/main/doc/load_data.png) | ![](https://raw.githubusercontent.com/ayonymus/Challenge/main/doc/market_data.png) | ![](https://raw.githubusercontent.com/ayonymus/Challenge/main/doc/coin_details.png) | ![](https://raw.githubusercontent.com/ayonymus/Challenge/main/doc/error.png)


## Tech used
I was following **CleanArchitecture** principles so that the code isn't tightly coupled.
On the view layer I have used **MVI** for producing predictable view states.
I opted for single `Activity` and `Compose` setup.

I have used the following more libraries:
* `Compose` for defining the UI
* `Coroutintes` and `Flow` for asynchronous data
* `ComposeNavigation` for handling navigation
* `Koin` for dependency injection
* `Room` for storing info on tokens
* `Retrofit` for network access

I have also added unit tests for a couple of critical paths, but not everything is covered.

## Package Structure and Modularization
The source code is organized in a meaningful way. There is separation by feature and by layers.
This approach makes it easy to make relatively independent components.
- `app` holds the core app infrastructure
- `core` contains the most common features
- `database` hold all Room classes and Daos
- `feature` packages for each feature, separately, not sharing data

- The `feature` packages are also divided by layers: `data`, `domain`, `usecase`, and `presentation`.

Currently the app is not modularized. However, the current package structure shows an approach how 
modularization could be implemented.

With this approach: the dependency graph would look something like this:

![](https://raw.githubusercontent.com/ayonymus/Challenge/main/doc/dependency_structure.png) 

Since the features are also subdivided into features, we could further improve module reusability
by slicing up by feature and layer. That way we could share whatever is necessary in a granular way.

## Improvements in the future

Currently there are no UI tests. Some components are not covered by regular unit tests.

Currently, only USD is supported for prices, and is hard coded in the `FakePreferencesProvider`

It would be nice to add a `preferences` screen so that user can change/select different currency.

UI is a bit ugly. Styling and UI could be massively improved.

Setting up ktlint or Detekt would improve code formatting issues.

Modularization would be nice.
