const plugins = [
  [
    "import",
    {
      libraryName: "vant",
      libraryDirectory: "es",
      style: true
    },
    "vant"
  ]
];

module.exports = {
  presets: ["@vue/app"],
  plugins
};
