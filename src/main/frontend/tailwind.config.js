/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["../resources/templates/**/*.{html,js}"],
  theme: {
    extend: {
      gridRowStart: {
        '13': '13',
        '14': '14',
        '15': '15',
        '16': '16',
        '17': '17',
        '18': '18',
        '19': '19',
        '20': '20',
        '21': '21',
        '22': '22',
        '23': '23',
        '24': '24',
        '25': '25',
        '26': '26',
        '27': '27',
      },
      gridRow: {
        'span-28': 'span 28 / span 28',
      }
    },
  },
  plugins: [],
  safelist: [
    { pattern: /col-start-\d+/ },
    { pattern: /row-start-\d+/ },
    { pattern: /row-span-\d+/ }
  ]
}