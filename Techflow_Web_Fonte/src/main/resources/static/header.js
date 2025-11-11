// Set theme from localStorage before anything else
var theme = localStorage.getItem('centaura-theme') || 'light';
document.body.className = theme;

// Sidebar include
fetch('sidebar.html').then(r => r.text()).then(html => {
  document.getElementById('sidebar-container').innerHTML = html;
  // Set theme class
  const sidebar = document.getElementById('sidebar-menu');
  if (sidebar) {
    sidebar.classList[theme === 'dark' ? 'add' : 'remove']('dark');
  }
  // Set active menu item
  if (window.location.pathname.endsWith(SYS_PAGE_NAME)) {
    document.getElementById(SYS_MENU_ID).classList.add('active');
  }
});

// Header include
fetch('header.html').then(r => r.text()).then(html => {
  document.getElementById('header-container').innerHTML = html;
  // Set header theme
  const header = document.getElementById('main-header');
  if (header) {
    header.className = 'navbar navbar-expand-lg fixed-top shadow-sm ' + (theme === 'dark' ? 'navbar-dark bg-dark' : 'navbar-light bg-light');
    // Set icon
    const icon = document.getElementById('theme-toggle-icon');
    if (icon) icon.innerHTML = theme === 'light' ? '🌙 Escuro' : '☀️ Claro';
  }
  // Theme toggle
  var themeBtn = document.getElementById('theme-toggle');
  if (themeBtn) {
    themeBtn.onclick = function() {
      theme = (theme === 'light' ? 'dark' : 'light');
      localStorage.setItem('centaura-theme', theme);
      document.body.className = theme;
      // Update sidebar theme
      var sidebar = document.getElementById('sidebar-menu');
      if (sidebar) {
        sidebar.classList[theme === 'dark' ? 'add' : 'remove']('dark');
      }
      // Update header theme
      if (header) {
        header.className = 'navbar navbar-expand-lg fixed-top shadow-sm ' + (theme === 'dark' ? 'navbar-dark bg-dark' : 'navbar-light bg-light');
        var icon = document.getElementById('theme-toggle-icon');
        if (icon) icon.innerHTML = theme === 'light' ? '🌙 Escuro' : '☀️ Claro';
      }
    };
  }
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
  // Username and click handler
  var techflow_session = JSON.parse(localStorage.getItem('techflow_session')) || {}
  var el = document.getElementById('header-username');
  if (el && techflow_session) {
    el.textContent = techflow_session.name || '';
    el.onclick = function() {
      if (confirm('Deseja realmente sair do sistema?')) {
        localStorage.removeItem('techflow_session');
        window.location.href = 'login.html';
      }
    };
  }
});
