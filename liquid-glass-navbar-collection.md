# Liquid Glass Navbar — Collection from SlickChord

Ready-to-port package. Copy each block into the corresponding file in the organize project.

---

## 1. Design tokens — `web/app/src/styles/tokens.css`

Insert after the `--glassMaterialBackdropFilter` line (light section), and mirror the dark variants inside `@media (prefers-color-scheme: dark)`.

```css
  /* Liquid glass navbar: stronger blur than the player pill, hairline edges. */
  --glassNavBlur: saturate(200%) blur(24px);
  --glassNavBG: rgba(var(--pageBG-rgb), 0.55);
  --glassNavHairline: var(--labelDivider);
  --glassNavSelection: rgba(var(--keyColor-rgb), 0.15);
  --glassNavSelectionHover: rgba(var(--keyColor-rgb), 0.25);
```

Dark section (`@media (prefers-color-scheme: dark)`), after `--glassMaterialBackground`:

```css
    /* Liquid glass navbar (dark). */
    --glassNavBG: rgba(var(--pageBG-rgb), 0.55);
```

(`--glassNavBlur`, `--glassNavHairline`, `--glassNavSelection`, `--glassNavSelectionHover` derive from `pageBG-rgb`, `labelDivider`, `keyColor-rgb` which already flip per theme.)

---

## 2. Focus ring — `web/app/src/styles/global.css`

Replace the `outline: none` block (Hard Gate R-32 fix):

```css
  /* Focus ring: visible only on keyboard focus (:focus-visible), per §8 of the design report.
   * 4px ring in --keyColor-focus-color with an offset so it clears rounded corners. */
  :focus-visible {
    outline: 4px solid var(--keyColor-focus-color);
    outline-offset: 2px;
  }
```

---

## 3. Sidebar — `web/app/src/shell/Sidebar.tsx`

Replace the `item` constant, the `icon` constant, and the `<nav>` element:

```tsx
  // A sidebar row; NavLink sets aria-current on the page's own row.
  const item =
    'group flex h-9 max-sm:h-[44px] items-center gap-2 mb-0.5 p-1 rounded-nav type-body-reduced text-primary hover:bg-[var(--glassNavSelectionHover)] aria-[current=page]:bg-[var(--glassNavSelection)] aria-[current=page]:text-key group-aria-[current=page]:icon:text-key';
  // Apple's glyphs, drawn at 24px (their 24-unit box), which the user preferred to Apple's own 28px.
  const icon = 'inline-grid size-6 flex-none place-items-center text-primary';
  const navLabel = 'min-w-0 truncate';
```

```tsx
      {/* Below 484px the sidebar is a drawer opened from the top bar's menu button. */}
      <nav
        className="absolute inset-y-0 left-0 z-(--z-web-chrome) flex w-(--web-navigation-width) flex-col [backdrop-filter:var(--glassNavBlur)] -webkit-backdrop-filter:var(--glassNavBlur) bg-[var(--glassNavBG)] border-r-[0.5px] border-r-[var(--glassNavHairline)] shadow-[0_0_0_1px_rgba(0,0,0,0.05)] max-sm:w-[min(300px,82vw)] max-sm:-translate-x-full max-sm:shadow-glass max-sm:transition-transform max-sm:duration-(--duration-panel) max-sm:ease-panel max-sm:data-[open=true]:translate-none"
        data-open={drawerOpen}
        aria-label="Main"
      >
```

Key behaviors:
- Stronger backdrop blur (`blur(24px)`) than the player pill — glass as the single accent (R-10).
- 0.5px hairline right border (`--glassNavHairline`).
- Selected/hover rows tinted key-color glass (`--glassNavSelection` / `--glassNavSelectionHover`).
- Selected icon + text go key color.
- Rows are `max-sm:h-[44px]` — 44px minimum tap target on mobile (R-03).

---

## 4. Mobile top bar — `web/app/src/shell/MobileTopBar.tsx`

Replace the `<header>` element:

```tsx
    <header className="hidden max-sm:absolute max-sm:inset-x-0 max-sm:top-0 max-sm:z-[calc(var(--z-web-chrome)-2)] max-sm:grid max-sm:h-(--mobile-top-bar-height) max-sm:grid-cols-[44px_1fr_44px] max-sm:items-center max-sm:px-3.5 [backdrop-filter:var(--glassNavBlur)] -webkit-backdrop-filter:var(--glassNavBlur) bg-[var(--glassNavBG)] border-b-[0.5px] border-b-[var(--glassNavHairline)] shadow-[0_0_0_1px_rgba(0,0,0,0.05)]">
      <button className="grid size-11 place-items-center rounded-full min-w-[44px] min-h-[44px]" aria-label="Open menu" onClick={() => setDrawerOpen(true)}>
        <Menu size={22} strokeWidth={2} />
      </button>
      <PrefetchLink to="/home" className="inline-flex items-center gap-1.5 justify-self-center type-[700_16px/20px] min-w-[44px] min-h-[44px]" aria-label="SlickChord, Home">
        <LogoMark size={20} className="text-primary" />
        <span>SlickChord</span>
      </PrefetchLink>
      <span className="w-11" />
    </header>
```

Key behaviors:
- Same glass blur/background/hairline as the sidebar; hairline on bottom edge.
- Menu button and wordmark both `min-w-[44px] min-h-[44px]` (R-03 tap target).

---

## 5. Design direction — `DESIGN.md`

Summary for the organize project:

```
Dial values (antislop Part 3):
- ENERGY 2 (Balanced): clean music player, red single accent, no buzzwords.
- RHYTHM 2 (Balanced): consistent shelves; headers differ from track lists and the player pill.
- MOTION 2 (Balanced): slide-in panel (0.3s), fade-in artwork, hover scrims (0.1s). No endless loops.

Palette (R-29): pageBG, system primary..quinary (text), key color (red accent). Neutrals not counted.
Typography: DM Sans, 400/500/600/700/800. Scale: header 34px/700, large title 26px/700, body 13px, callout 12px.
Radius (R-11): nav 8px, artwork small 5px/medium 7px/large 10px, pill 1000px, track row 6px.
Glass (R-10): at most 1–2 elements — sidebar, player pill, side panel. Cards/modals/shelves stay solid.
Mobile (R-03): below 484px sidebar = drawer; 52px glass top bar; targets ≥44px; no horizontal overflow.
```

---

## Verification checklist (before porting)

- [ ] `tsc` typecheck passes
- [ ] `vite build` passes
- [ ] No em dashes in UI text (R-02)
- [ ] `:focus-visible` ring present, no `outline: none` without replacement (R-32)
- [ ] Mobile targets ≥44px (R-03)
- [ ] Glass on ≤2 surfaces (R-10)

No changes are committed in this repo — this file is the portable collection. Apply the blocks above to the organize project, then run typecheck + build.
