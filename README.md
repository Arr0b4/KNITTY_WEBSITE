# KNITTY

Knitty is a work in progress, lovingly being customized and developed. More cozy details are on the way! ♡

# LOCALLY RUN
```
git clone https://github.com/Arr0b4/KNITTY_WEBSITE.git
cd KNITTY_WEBSITE/knitty_frontend
npm ci
npm run dev
```
Open the local address printed by Vite (usually http://localhost:5173). If that port is busy, Vite may use another one.

Windows PowerShell: If npm is blocked by script execution policy, use npm.cmd ci and npm.cmd run dev instead.

##Project General Structure
```js
knitty_frontend/
├── public/          # Static assets
├── src/
│   ├── assets/      # Images, icons and fonts
│   ├── components/  # Reusable UI
│   ├── view/        # Pages and page sections
│   ├── api/         # API requests
│   └── main.tsx     # App entry point
├── package.json
└── vite.config.ts

```
