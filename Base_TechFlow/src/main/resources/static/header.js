// Header include
fetch('header.html').then(r => r.text()).then(html => {
  document.getElementById('header-container').innerHTML = html;
  // Set header theme
  const header = document.getElementById('main-header');
  if (header) {
    header.className = 'navbar navbar-expand-lg fixed-top shadow-sm ' + (app.theme === 'dark' ? 'navbar-dark bg-dark' : 'navbar-light bg-light');
    // Set icon
    const icon = document.getElementById('theme-toggle-icon');
    if (icon) icon.innerHTML = app.theme === 'light' ? '🌙 Escuro' : '☀️ Claro';
  }
  // Theme toggle
  document.getElementById('theme-toggle').onclick = function() {
    app.theme = app.theme === 'light' ? 'dark' : 'light';
    localStorage.setItem('centaura-theme', app.theme);
    document.body.className = app.theme;
    // Update sidebar theme
    const sidebar = document.getElementById('sidebar-menu');
    if (sidebar) {
      sidebar.classList.toggle('dark', app.theme === 'dark');
    }
    // Update header theme
    if (header) {
      header.className = 'navbar navbar-expand-lg fixed-top shadow-sm ' + (app.theme === 'dark' ? 'navbar-dark bg-dark' : 'navbar-light bg-light');
      const icon = document.getElementById('theme-toggle-icon');
      if (icon) icon.innerHTML = app.theme === 'light' ? '🌙 Escuro' : '☀️ Claro';
    }
  };
  // Hamburger menu (wait for sidebar)
  function attachSidebarToggle() {
    var btn = document.getElementById('menu-toggle');
    var sidebar = document.getElementById('sidebar-menu');
    if (btn && sidebar) {
      btn.onclick = function() {
        sidebar.classList.toggle('d-none');
        document.body.classList.toggle('sidebar-hidden');
      };
    } else {
      setTimeout(attachSidebarToggle, 100);
    }
  }
  attachSidebarToggle();
});
// Sidebar include
fetch('sidebar.html').then(r => r.text()).then(html => {
  document.getElementById('sidebar-container').innerHTML = html;
  // Set theme class
  const sidebar = document.getElementById('sidebar-menu');
  if (sidebar) {
    sidebar.classList.toggle('dark', app.theme === 'dark');
  }
  // Set active menu item
  if (window.location.pathname.endsWith(SYS_PAGE_NAME)) {
    document.getElementById(SYS_MENU_ID).classList.add('active');
  }
});
