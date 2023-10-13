import type { Config } from 'tailwindcss'

const config: Config = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      colors: {
        "purple": "#1D001C",
        "white": "#FDFDE1",
        "pink": "#E06B88",
        "orange": "#E28B7A"
      }
    },
  },
  plugins: [],
}
export default config
