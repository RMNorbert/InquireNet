/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        lightgray: '#CECED0',
        buttonBlue: '#533CE2'
      }
    },
  },
  plugins: [],
}
