///无参数请求回调
typedef ParamVoidCallback = dynamic Function();

///回调一个参数
typedef Param1Callback<D> = dynamic Function(D data);

///回到俩个参数
typedef Param2Callback<O, T> = dynamic Function(O dataOne, T dataTwo);

///回调三个参数
typedef Param3Callback<O, T, K> = dynamic Function(
    O dataOne, T dataTwo, K threeData);