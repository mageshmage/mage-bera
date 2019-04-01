module.exports = function (api) {
  api.cache(false);

  return {
    presets: [
      '@babel/preset-react'
    ],
    plugins: [
      '@babel/plugin-syntax-dynamic-import',
      'babel-plugin-syntax-async-functions',
      'babel-plugin-transform-class-properties'
    ]
  }
}
