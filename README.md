# poiexcel
A maven plugin that will read excel which contains url, this plugin will validate url and fill the url cell in 
excel as green or red. Then based on the url, a stock check(i.e. whether an item is in stock or not) will be performed.
Currently only k3.cn and sooxie.com website are supported.

User could add their own logic based on jsoup cssQuery.
