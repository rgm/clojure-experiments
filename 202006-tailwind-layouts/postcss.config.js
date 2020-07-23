module.exports = {
  plugins: [
    require('autoprefixer'),
    require("tailwindcss"),
    require("cssnano")({ preset: "default" })
  ]
};
